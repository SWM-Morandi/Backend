package swm_nm.morandi.aop.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sentry.*;
import io.sentry.protocol.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import swm_nm.morandi.global.utils.SecurityUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private EntityManager em;

    //SQL 성능 측정 시 해당 메서드에 @Logging 어노테이션 붙여서 사용
    @Pointcut("@annotation(swm_nm.morandi.aop.annotation.Logging)")
    public void loggingPointcut() {
    }

    @Around("loggingPointcut()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // 영속 컨텍스트 초기화
        em.clear();
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature() + " 실행 시간 " + elapsedTime + "ms");
        return result;
    }

    @Around("bean(*Controller)")
    public Object controllerAroundLogging(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //Sentry 사용자 식별을 위한 User 인스턴스 생성
        User user = new User();
        //로그인 이전의 Controller에서도 이 함수에 들어오기 때문에 Security Context에 있는 MemberId를 가져올 수 없다.
        try {
            user.setUsername(SecurityUtils.getCurrentMemberId().toString());
        } catch (Exception e) {
            user.setUsername("before Login User");
        }
        user.setIpAddress("{{auto}}");

        //Sentry 식별 사용자 정보 등록
        Sentry.setUser(user);

        Breadcrumb requestBreadcrumb = new Breadcrumb();
        requestBreadcrumb.setData("url", request.getRequestURL().toString());
        requestBreadcrumb.setData("method", request.getMethod());
        Sentry.addBreadcrumb(requestBreadcrumb);

        String callFunction = pjp.getSignature().getName();


        if (!request.getMethod().equalsIgnoreCase("GET") && request.getContentType() != null && request.getContentType().contains("application/json")) {
            StringBuilder requestBody = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            Breadcrumb bodyBreadcrumb = new Breadcrumb();
            bodyBreadcrumb.setData("body", requestBody.toString());
            Sentry.addBreadcrumb(bodyBreadcrumb);
        } else {
            Breadcrumb paramBreadcrumb = new Breadcrumb();
            paramBreadcrumb.setData("parameters", request.getParameterMap());
            Sentry.addBreadcrumb(paramBreadcrumb);
        }

        Object result = "";
        ITransaction transaction = Sentry.startTransaction(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName(), "http");
        try {
            result = pjp.proceed();
            transaction.setStatus(SpanStatus.OK);
            return result;
        }
        catch (Exception e) {
            transaction.setThrowable(e);
            transaction.setStatus(SpanStatus.INTERNAL_ERROR);
            log.error("An error occurred: {}", e.getMessage());
            throw e;
        }
        finally {
            log.info("{}, \"return\": {}", callFunction,
                    mapper.writeValueAsString(result)
                    );

            Sentry.configureScope(Scope::clear);
            Sentry.clearBreadcrumbs();
        }

    }

}




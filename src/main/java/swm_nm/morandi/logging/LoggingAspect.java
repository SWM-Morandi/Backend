package swm_nm.morandi.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sentry.Scope;
import io.sentry.Sentry;
import io.sentry.protocol.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import swm_nm.morandi.auth.security.SecurityUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(@swm_nm.morandi.logging.Logging *)")
    public void pointCut(){
    }

    private ObjectMapper mapper = new ObjectMapper();

    private String host;
    private String ip;


    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        this.host = addr.getHostName();
        this.ip = addr.getHostAddress();
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
        user.setIpAddress(request.getRemoteAddr());

        //Sentry 식별 사용자 젖ㅇ보 등록
        Sentry.setUser(user);

        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
        String msg = String.format("[hostname]: %s, [hostIP]: %s, [clientIP]: %s, [clientURL]: %s ",
                host, ip,
                request.getRemoteAddr(),
                request.getRequestURL().toString()
        );

        log.info("[REQUEST], [callFunction]: {}, [parameter]: {}, {}",callFunction
                ,mapper.writeValueAsString(request.getParameterMap())
                ,msg);

        Object result = pjp.proceed();


        log.info("[RESPONSE] [callFunction]: {}, [parameter]: {}, {}", callFunction,
                mapper.writeValueAsString(result),
                msg);

        Sentry.configureScope(Scope::clear);

        return result;
}
//
//
//    @Before("pointCut()")
//    public void before(JoinPoint jp)
//    {
//
//
//        log.info("Calling method: [{}] in class: [{}]", jp.getSignature().getName(), jp.getTarget().getClass().toString());
//
//    }
//
//    @After("pointCut()")
//    public void after(JoinPoint jp)
//    {
//        log.info("Exiting method: [{}] in class: [{}]", jp.getSignature().getName(), jp.getTarget().getClass().toString());
//
//    }
}




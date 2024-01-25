package swm_nm.morandi.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import swm_nm.morandi.global.exception.MorandiException;
import swm_nm.morandi.global.exception.errorcode.SubmitErrorCode;
import swm_nm.morandi.global.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaekjoonLoginInterceptor implements HandlerInterceptor {

    @Value("${extension.activated}")
    private Boolean extensionActivated;

    private final RedisTemplate<String, Object> redisTemplate;
    private String generateKey(Long memberId) {
        return String.format("OnlineJudgeCookie:memberId:%s", memberId);
    }

    @Override   
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long memberId = SecurityUtils.getCurrentMemberId();
        String key = generateKey(memberId);
        Boolean hasKey = redisTemplate.hasKey(key);

        if (extensionActivated && (hasKey == null || !hasKey)) {
            throw new MorandiException(SubmitErrorCode.COOKIE_NOT_EXIST);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}

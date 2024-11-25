package swm_nm.morandi.aop.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import swm_nm.morandi.global.exception.MorandiException;
import swm_nm.morandi.global.exception.errorcode.LockErrorCode;
import swm_nm.morandi.global.utils.SecurityUtils;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class MemberLockAspect {

    private final RedissonClient redissonClient;

    private final StringRedisTemplate redisTemplate;
    private final String MEMBER_LOCK_KEY = "memberLock";
    @Pointcut("@annotation(swm_nm.morandi.aop.annotation.MemberLock)")
    public void memberLockPointcut() {
    }

    @Around("memberLockPointcut()")
    public Object MEMBERLock(ProceedingJoinPoint joinPoint) throws Throwable {
        Long memberId = SecurityUtils.getCurrentMemberId();
        String memberLockKey = String.format("%s:%d", MEMBER_LOCK_KEY, memberId);
        RLock lock = redissonClient.getLock(memberLockKey);
        boolean locked = false;
        try {
            locked = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if (!locked) {
                throw new MorandiException(LockErrorCode.MEMBER_LOCKED);
            }
            return joinPoint.proceed();
        } finally {
            if (locked) {
                unlock(memberLockKey);
            }
        }
    }
    private void unlock(String key) {
        redisTemplate.delete(key);
    }

}


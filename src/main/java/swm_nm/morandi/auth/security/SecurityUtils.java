package swm_nm.morandi.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SecurityUtils {
    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("SecurityContextNotFoundException");
        }

        //이부분 나중에 손보자
        if (authentication.isAuthenticated()
                && !CollectionUtils.containsAny(
                authentication.getAuthorities(), List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"), new SimpleGrantedAuthority("ROLE_SWAGGER")))) {
            return Long.valueOf(authentication.getName());
        }
        // 스웨거 유저일시 익명 유저 취급
        // 익명유저시 userId 0 반환
        return 0L;
    }

}

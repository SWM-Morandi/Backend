package swm_nm.morandi.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final AccessDeniedFilter accessDeniedFilter;
//    private final JwtExceptionFilter jwtExceptionFilter;

    @Override
    public void configure(HttpSecurity builder) {
        builder.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
//        builder.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
//        builder.addFilterBefore(accessDeniedFilter, FilterSecurityInterceptor.class);
    }
}

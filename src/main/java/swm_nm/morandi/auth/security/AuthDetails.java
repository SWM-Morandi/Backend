package swm_nm.morandi.auth.security;


import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import swm_nm.morandi.member.domain.Role;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class AuthDetails implements UserDetails {

    private String userId;
//    private Role role;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
//        return Collections.singletonList(new SimpleGrantedAuthority(Role.MEMBER.getRole()));
      //  new SimpleGrantedAuthority(role.getRole());
    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

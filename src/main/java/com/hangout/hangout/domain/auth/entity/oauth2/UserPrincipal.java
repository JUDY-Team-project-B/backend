package com.hangout.hangout.domain.auth.entity.oauth2;

import com.hangout.hangout.domain.user.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * user 정보를 spring security로 전달해주는 class
 */
@Getter
@Builder
public class UserPrincipal implements OAuth2User, UserDetails {

    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        return UserPrincipal.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .attributes(attributes)
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
            .build();
    }

    @Override
    public String getUsername() {
        return email;
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

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}

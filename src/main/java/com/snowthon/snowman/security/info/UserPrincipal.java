package com.snowthon.snowman.security.info;


import com.snowthon.snowman.contrant.Constants;
import com.snowthon.snowman.dto.type.ERole;
import com.snowthon.snowman.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    @Getter private final Long id;
    @Getter private final ERole role;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal createByUserSecurityForm(UserRepository.UserSecurityForm form) {
        return UserPrincipal.builder()
                .id(form.getId())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(Constants.USER_ROLE)))
                .build();
    }

    public static UserPrincipal createByUserId(Long userId) {
        return UserPrincipal.builder()
                .id(userId)
                .authorities(Collections.singleton(new SimpleGrantedAuthority(Constants.USER_ROLE)))
                .build();
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

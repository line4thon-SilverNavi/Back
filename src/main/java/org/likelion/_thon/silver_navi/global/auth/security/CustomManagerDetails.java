package org.likelion._thon.silver_navi.global.auth.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.likelion._thon.silver_navi.domain.manager.entity.Manager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomManagerDetails implements UserDetails {

    private final Manager manager;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(manager.getRole().getStringRole()));
    }

    @Override
    public String getPassword() { return manager.getPassword(); }

    @Override
    public String getUsername() { return String.valueOf(manager.getId()); }
}

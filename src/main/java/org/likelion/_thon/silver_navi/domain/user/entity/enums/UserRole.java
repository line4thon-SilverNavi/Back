package org.likelion._thon.silver_navi.domain.user.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    @Getter
    private final String userRole;

    public String getStringRole() {
        return userRole;
    }
}

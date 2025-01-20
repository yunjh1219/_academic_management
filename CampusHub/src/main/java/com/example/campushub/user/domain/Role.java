package com.example.campushub.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
    private final String key;
}

/**
 * Role -> ADMIN, USER
 * Role.ADMIN.getKey() -> ROLE_ADMIN
 *
 * Role.USER.getKey() -> "ROLE_USER"
 */
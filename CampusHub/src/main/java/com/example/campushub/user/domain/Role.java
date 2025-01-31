package com.example.campushub.user.domain;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
    private final String key;

    public static Role ofValue(String value) {
		Role result;

		if (Objects.equals(value, "ROLE_ADMIN")) {
			result = Role.ADMIN;
		} else if (Objects.equals(value, "ROLE_USER")) {
			result = Role.USER;
		} else {
			result = null;
		}

		return result;
	}
}

/**
 * Role -> ADMIN, USER
 * Role.ADMIN.getKey() -> ROLE_ADMIN
 *
 * Role.USER.getKey() -> "ROLE_USER"
 */
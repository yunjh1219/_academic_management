package com.example.campushub.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {
    STUDENT("TYPE_STUDENT"), PROFESSOR("TYPE_PROFESSOR"), ADMIN("TYPE_ADMIN");
    private final String key;

}

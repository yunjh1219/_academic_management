package com.example.campushub.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeType {

    BACHELOR("학사"), PROFESSOR("교수");

    private final String name;

    public static NoticeType of(String noticeType) {

        for (NoticeType type : NoticeType.values()) {
            if (type.name.equals(noticeType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("알 수 없는 공지 유형입니다: " + noticeType);
    }
}

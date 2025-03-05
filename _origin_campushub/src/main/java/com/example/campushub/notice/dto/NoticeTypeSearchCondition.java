package com.example.campushub.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeTypeSearchCondition {
    @NotBlank(message = "타입을 선택해주세요")
    private String noticeType;

    @Builder
    public NoticeTypeSearchCondition(String noticeType) {

        this.noticeType = noticeType;
    }
}

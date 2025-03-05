package com.example.campushub.notice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEditDto {

    private String noticeType;
    private String noticeTitle;
    private String noticeContent;

    @Builder
    public NoticeEditDto(String noticeType, String noticeTitle, String noticeContent) {
        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
    }
}

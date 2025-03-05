package com.example.campushub.notice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeSearchCondition {

    private String noticeType;
    private String noticeTitle;
    private LocalDateTime startDate; // 시작 날짜
    private LocalDateTime endDate;   // 종료 날짜

    @Builder
    public NoticeSearchCondition(String noticeType, String noticeTitle, LocalDateTime startDate, LocalDateTime endDate) {

        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}

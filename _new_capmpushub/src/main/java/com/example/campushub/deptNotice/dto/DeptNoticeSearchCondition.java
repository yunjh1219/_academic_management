package com.example.campushub.deptNotice.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptNoticeSearchCondition {

    private String deptNoticeType;   // 타입
    private String deptName;         // 학과명
    private String noticeTitle;
    private LocalDateTime startDate; // 시작 날짜
    private LocalDateTime endDate;   // 종료 날짜

    @Builder
    public DeptNoticeSearchCondition(String deptNoticeType, String deptName, String noticeTitle, LocalDateTime startDate, LocalDateTime endDate) {

        this.deptNoticeType = deptNoticeType;
        this.deptName = deptName;
        this.noticeTitle = noticeTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}

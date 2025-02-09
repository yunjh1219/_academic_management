package com.example.campushub.deptNotice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptNoticeEditDto {

    private String deptNoticeType;
    private String deptName;
    private String noticeTitle;
    private String noticeContent;

    @Builder
    public DeptNoticeEditDto(String deptNoticeType, String deptName, String noticeTitle, String noticeContent) {
        this.deptNoticeType = deptNoticeType;
        this.deptName = deptName;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
    }

}

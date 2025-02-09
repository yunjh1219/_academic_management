package com.example.campushub.deptNotice.dto;

import com.example.campushub.deptNotice.domain.DeptNoticeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptNoticeFindOneResponseDto {

    private Long id;                         // 아이디
    private DeptNoticeType deptNoticeType;   // 타입
    private String deptName;                 // 학과명
    private String userName;                 // 작성자
    private String noticeTitle;              // 제목
    private String noticeContent;            // 내용
    private LocalDateTime createdAt;         // 생성일
    private LocalDateTime updatedAt;         // 수정일

    @Builder
    @QueryProjection
    public DeptNoticeFindOneResponseDto(Long id, DeptNoticeType deptNoticeType, String deptName, String userName, String noticeTitle, String noticeContent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.deptNoticeType = deptNoticeType;
        this.deptName = deptName;
        this.userName = userName;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}

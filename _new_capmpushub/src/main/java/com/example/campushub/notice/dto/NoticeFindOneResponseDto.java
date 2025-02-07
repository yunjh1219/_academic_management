package com.example.campushub.notice.dto;

import com.example.campushub.notice.domain.NoticeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFindOneResponseDto { //← 단건조회

    private Long id;
    private NoticeType noticeType;   // 타입
    private String userName;         // 유저 이름
    private String noticeTitle;      // 제목
    private String noticeContent;    // 내용
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일

    @Builder
    @QueryProjection
    public NoticeFindOneResponseDto(Long id, NoticeType noticeType, String userName, String noticeTitle, String noticeContent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.noticeType = noticeType;
        this.userName = userName;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

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
public class NoticeFindAllResponseDto { //← 전체조회

    private Long id;                 // 아이디
    private NoticeType noticeType;   // 타입
    private String userName;         // 유저 이름
    private String noticeTitle;      // 제목
    private LocalDateTime createdAt; // 생성일


    @Builder
    @QueryProjection
    public NoticeFindAllResponseDto(Long id, NoticeType noticeType, String userName, String noticeTitle, LocalDateTime createdAt) {
        this.id = id;
        this.noticeType = noticeType;
        this.userName = userName;
        this.noticeTitle = noticeTitle;
        this.createdAt = createdAt;
    }

}

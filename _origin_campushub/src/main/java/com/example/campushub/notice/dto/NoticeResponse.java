package com.example.campushub.notice.dto;

import com.example.campushub.notice.domain.NoticeType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeResponse {

    private Long id; //아이디
    private NoticeType noticeType;
    private String noticeTitle; //제목
    private String noticeContent; //내용
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일
    private String userName; // 유저 이름


    @Builder
    @QueryProjection
    public NoticeResponse(Long id, NoticeType noticeType,String noticeTitle, String noticeContent, LocalDateTime createdAt, LocalDateTime updatedAt, String userName) {
        this.id = id;
        this.noticeType = noticeType;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
    }


}

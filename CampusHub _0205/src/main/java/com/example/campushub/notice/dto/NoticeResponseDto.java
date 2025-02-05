package com.example.campushub.notice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {
    private Long id; //아이디
    private String noticeTitle; //제목
    private String noticeContent; //내용
    private String createdAt; // 생성일
    private String updatedAt; // 수정일
    private String userName; // 유저 이름

    @Builder
    @QueryProjection
    public NoticeResponseDto(Long id, String noticeTitle, String noticeContent, String createdAt, String updatedAt, String userName) {
        this.id = id;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
    }
}

package com.example.campushub.notice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NoticeCreateRequestDto {
    private String title;   // 공지사항 제목
    private String content; // 공지사항 내용

    @Builder
    @QueryProjection
    public NoticeCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
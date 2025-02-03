package com.example.campushub.notice.dto;

import com.example.campushub.notice.domain.Notice;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class NoticeResponseDto {

    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private String createdBy;

    @Builder
    @QueryProjection
    public NoticeResponseDto(Long id, String title, String content, Date createdAt, String createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}

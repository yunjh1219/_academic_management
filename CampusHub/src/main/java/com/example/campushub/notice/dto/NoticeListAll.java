package com.example.campushub.notice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class NoticeListAll {

    private Long noticeId;
    private String noticeTitle;
    private Date noticeCreatedAt;
    private Long userId;
    private String userName;

    @Builder
    @QueryProjection
    public NoticeListAll(Long noticeId, String noticeTitle, Date noticeCreatedAt, Long userId, String userName) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeCreatedAt = noticeCreatedAt;
        this.userId = userId;
        this.userName = userName;
    }

}

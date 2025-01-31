package com.example.campushub.notice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeSearchCondition {

    private String filter;  // "title" 또는 "createdBy"
    private String keyword; // 검색어

    @Builder
    @QueryProjection
    public NoticeSearchCondition(String filter, String keyword) {
        this.filter = filter;
        this.keyword = keyword;
    }
}

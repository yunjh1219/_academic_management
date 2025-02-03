package com.example.campushub.departmentnotice.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class DepartmentNoticeUpdateRequestDto {
    @NotNull
    private Long id; // 수정할 공지사항 ID

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Builder
    @QueryProjection
    public DepartmentNoticeUpdateRequestDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
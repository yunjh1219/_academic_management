package com.example.campushub.departmentnotice.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
public class DepartmentNoticeRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "UserCourse ID는 필수입니다.")
    private Long userCourseId; // 관련 강의(UserCourse ID)

    @Builder
    @QueryProjection
    public DepartmentNoticeRequestDto(String title, String content, Long userCourseId) {
        this.title = title;
        this.content = content;
        this.userCourseId = userCourseId;
    }
}

package com.example.campushub.usercourse.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserCourseResponseDto {
    private Long userCourseId; // UserCourse의 ID
    private String courseName; // 강의명

    @Builder
    @QueryProjection
    public UserCourseResponseDto(Long userCourseId, String courseName) {
        this.userCourseId = userCourseId;
        this.courseName = courseName;
    }
}
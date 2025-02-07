package com.example.campushub.attendance.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceSearchCourseCondition {
    private String courseName;

    @Builder
    public AttendanceSearchCourseCondition(String courseName) {
        this.courseName = courseName;
    }

    public static AttendanceSearchCourseCondition of(String courseName) {
        return AttendanceSearchCourseCondition.builder()
                .courseName(courseName)
                .build();
    }
}

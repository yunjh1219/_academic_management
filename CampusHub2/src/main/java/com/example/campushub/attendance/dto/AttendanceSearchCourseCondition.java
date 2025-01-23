package com.example.campushub.attendance.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

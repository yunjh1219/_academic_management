package com.example.campushub.attendance.dto;


import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceSearchCondition {
    private String courseName;
    private Week week;

    @Builder
    public AttendanceSearchCondition(String courseName, Week week) {
        this.courseName = courseName;
        this.week = week;
    }

    public static AttendanceSearchCondition of(String courseName, Week week) {
        return AttendanceSearchCondition.builder()
                .courseName(courseName)
                .week(week)
                .build();
    }

}

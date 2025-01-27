package com.example.campushub.attendance.dto;

import com.example.campushub.attendance.domain.AttendanceStatus;
import com.example.campushub.course.domain.CourseDay;
import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceUserDto {
 private Week week;
 private AttendanceStatus status;


 @Builder
 @QueryProjection
    public AttendanceUserDto(Week week, AttendanceStatus status) {
     this.week = week;
     this.status = status;
 }
}


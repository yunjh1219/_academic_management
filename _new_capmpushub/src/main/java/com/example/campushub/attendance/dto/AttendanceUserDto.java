package com.example.campushub.attendance.dto;

import com.example.campushub.attendance.domain.AttendanceStatus;
import com.example.campushub.course.domain.CourseDay;
import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceUserDto {
 private String week;
 private String status;


 @Builder
 @QueryProjection
    public AttendanceUserDto(Week week, AttendanceStatus status) {
     this.week = week.getName();
     this.status = (status != null) ? status.getKoreanName() : null;
 }
}


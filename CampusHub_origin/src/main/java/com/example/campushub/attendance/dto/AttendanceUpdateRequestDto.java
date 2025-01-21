package com.example.campushub.attendance.dto;


import com.example.campushub.attendance.domain.AttendanceStatus;
import com.example.campushub.nweek.domain.Week;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceUpdateRequestDto {
    private String CourseName;
    private Week week;
    private String userNum;
    private AttendanceStatus status;

    @Builder
    public AttendanceUpdateRequestDto(String courseName, Week week, String userNum, AttendanceStatus status) {
        this.CourseName = courseName;
        this.week = week;
        this.userNum = userNum;
        this.status = status;
    }
}

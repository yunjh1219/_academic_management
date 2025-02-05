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
    private String week;
    private String userNum;
    private String attendanceStatus;

    @Builder
    public AttendanceUpdateRequestDto(String courseName, String week, String userNum, String status) {
        this.CourseName = courseName;
        this.week = week;
        this.userNum = userNum;
        this.attendanceStatus = status;
    }
}

package com.example.campushub.attendance.dto;


import com.example.campushub.attendance.domain.AttendanceStatus;
import com.example.campushub.nweek.domain.Week;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllAttendanceResponseDto {
    private String userName;
    private String userNum;
    private AttendanceStatus status;
    private Week week;


    @Builder
    @QueryProjection
    public AllAttendanceResponseDto(String userName, String userNum, AttendanceStatus status, Week week) {
        this.userName = userName;
        this.userNum = userNum;
        this.status = status;
        this.week = week;

    }
}

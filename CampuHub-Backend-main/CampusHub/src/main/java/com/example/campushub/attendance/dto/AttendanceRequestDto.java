package com.example.campushub.attendance.dto;


import com.example.campushub.attendance.domain.AttendanceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceRequestDto {
    private String userName;
    private String userNum;
    private AttendanceStatus status;


    @Builder
    public AttendanceRequestDto(String userName, String userNum, AttendanceStatus status) {
        this.userName = userName;
        this.userNum = userNum;
        this.status = status;
    }
}

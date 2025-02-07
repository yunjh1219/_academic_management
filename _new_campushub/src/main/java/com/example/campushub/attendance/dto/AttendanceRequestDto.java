package com.example.campushub.attendance.dto;


import com.example.campushub.attendance.domain.AttendanceStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceRequestDto {
    private String userName;
    private String userNum;
    private String status;


    @Builder
    public AttendanceRequestDto(String userName, String userNum, String status) {
        this.userName = userName;
        this.userNum = userNum;
        this.status = status;
    }
}

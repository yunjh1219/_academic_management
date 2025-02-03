package com.example.campushub.attendance.dto;

import com.example.campushub.attendance.domain.AttendanceStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceResponseDto {
    private String userName;
    private String userNum;
    private String status;
    @Builder
    @QueryProjection
    public AttendanceResponseDto(String userName, String userNum, AttendanceStatus status) {
        this.userName = userName;
        this.userNum = userNum;
        this.status = status.getKoreanName();
    }
}

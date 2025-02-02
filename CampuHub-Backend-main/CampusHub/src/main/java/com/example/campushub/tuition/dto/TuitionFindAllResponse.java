package com.example.campushub.tuition.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TuitionFindAllResponse {
    private String username;
    private String userNum;
    private String deptName;
    private String userType;
    private String paymentStatus;
    private LocalDate paymentDate;

    @Builder
    @QueryProjection
    public TuitionFindAllResponse(String username, String userNum, String deptName, String userType,
                                  String paymentStatus, LocalDate paymentDate) {
        this.username = username;
        this.userNum = userNum;
        this.deptName = deptName;
        this.userType = userType;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }
}

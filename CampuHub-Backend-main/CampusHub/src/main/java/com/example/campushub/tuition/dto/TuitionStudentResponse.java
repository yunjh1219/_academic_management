package com.example.campushub.tuition.dto;


import com.example.campushub.schoolyear.domain.Semester;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TuitionStudentResponse {
    private String userNum;
    private String userName;
    private String deptName;
    private LocalDate year;
    private Semester semester;
    private int tuitionFee;
    private int scholarshipAmount;
    private int totalAmount;
    private String paymentStatus;

    @Builder
    @QueryProjection
    public TuitionStudentResponse(String userNum, String userName, String deptName, LocalDate year,
                                  Semester semester, int tuitionFee, int scholarshipAmount ,String paymentStatus){

        this.userNum = userNum;
        this.userName = userName;
        this.deptName = deptName;
        this.year = year;
        this.semester = semester;
        this.tuitionFee = tuitionFee;
        this.scholarshipAmount = scholarshipAmount;
        this.paymentStatus = paymentStatus;
        this.totalAmount = tuitionFee - scholarshipAmount;


    }


}

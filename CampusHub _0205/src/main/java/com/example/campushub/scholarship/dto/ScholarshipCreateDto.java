package com.example.campushub.scholarship.dto;

import com.example.campushub.scholarship.domain.PaymentType;
import com.example.campushub.scholarship.domain.Scholarship;
import com.example.campushub.schoolyear.domain.SchoolYear;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class  ScholarshipCreateDto {

    private String userNum;
    private String userName;
    private String deptName;
    private String year;
    private String semester;
    //private
    @NotBlank(message = "장학금 명을 입력하세요")
    private String scholarshipName;
    @NotBlank(message = "지급 구분을 입력하세요")
    private String paymentType;
    @NotBlank(message = "금액을 입력하세요")
    private int amount;

    @Builder
    public ScholarshipCreateDto(String userNum, String userName, String deptName, String year, String semester, String scholarshipName, int amount, String paymentType) {
        this.userNum = userNum;
        this.userName = userName;
        this.deptName = deptName;
        this.year = year;
        this.semester = semester;
        this.scholarshipName = scholarshipName;
        this.amount = amount;
        this.paymentType = paymentType;

    }

    public Scholarship toEntity() {
        return Scholarship.builder()
            .scholarshipName(scholarshipName)
            .type(PaymentType.of(paymentType))
            .amount(amount)
            .build();

    }

}

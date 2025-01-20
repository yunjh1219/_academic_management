package com.example.campushub.scholarship.dto;

import com.example.campushub.scholarship.domain.PaymentType;
import com.example.campushub.scholarship.domain.Scholarship;
import com.example.campushub.schoolyear.domain.SchoolYear;

import com.example.campushub.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScholarshipCreateDto {


    private User user;
    private String userNum;
    private String userName;
    private String deptName;
    private SchoolYear schoolYear;
    @NotBlank(message = "장학금 명을 입력하세요")
    private String scholarshipName;
    @NotBlank(message = "지급 구분을 입력하세요")
    private PaymentType type;
    @NotBlank(message = "금액을 입력하세요")
    private int amout;

    @Builder
    public ScholarshipCreateDto(String userNum, String userName, String deptName, SchoolYear schoolYear, String scholarshipName, int amout, PaymentType type) {
        this.userNum = userNum;
        this.userName = userName;
        this.deptName = deptName;
        this.schoolYear = schoolYear;
        this.scholarshipName = scholarshipName;
        this.amout = amout;
        this.type = type;

    }

    public Scholarship toEntity() {
        return Scholarship.builder()
            .scholarshipName(scholarshipName)
            .type(type)
            .amount(amout)
            .build();

    }

}

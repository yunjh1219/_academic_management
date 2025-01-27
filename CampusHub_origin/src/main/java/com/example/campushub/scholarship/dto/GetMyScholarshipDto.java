package com.example.campushub.scholarship.dto;


import com.example.campushub.scholarship.domain.PaymentType;
import com.example.campushub.schoolyear.domain.Semester;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class GetMyScholarshipDto {

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate year;
    private Semester semester;
    private String scholarshipName;
    private PaymentType type;
    private int amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate confDate;

    @Builder
    @QueryProjection
    public GetMyScholarshipDto(LocalDate year, Semester semester, String scholarshipName, PaymentType type, int amount, LocalDate confDate) {
        this.year = year;
        this.semester = semester;
        this.scholarshipName = scholarshipName;
        this.type = type;
        this.amount = amount;
        this.confDate = confDate;
    }

}

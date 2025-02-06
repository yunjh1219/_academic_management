package com.example.campushub.scholarship.dto;


import com.example.campushub.scholarship.domain.PaymentType;
import com.example.campushub.schoolyear.domain.Semester;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Year;

@Getter
@NoArgsConstructor
public class GetMyScholarshipDto {

    private String year;
    private String semester;
    private String scholarshipName;
    private String type;
    private int amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate confDate;

    @Builder
    @QueryProjection
    public GetMyScholarshipDto(Year year, Semester semester, String scholarshipName, PaymentType type, int amount, LocalDate confDate) {
        this.year = year.toString();
        this.semester = semester.getName();
        this.scholarshipName = scholarshipName;
        this.type = type.getName();
        this.amount = amount;
        this.confDate = confDate;
    }

}

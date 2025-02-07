package com.example.campushub.scholarship.dto;


import com.example.campushub.scholarship.domain.PaymentType;
import com.example.campushub.schoolyear.domain.Semester;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@Getter
@NoArgsConstructor
public class ScholarshipResponseDto {

 private String username;
 private String userNum;
 private String deptName;
 private String year;
 private String semester;
 private String scholarshipName;
 private String paymentType;
 private int amount;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private LocalDate confDate;

 @Builder
 @QueryProjection
 public ScholarshipResponseDto(String username, String userNum ,String deptName, Year year, Semester semester, String scholarshipName, PaymentType type, int amount, LocalDate confDate ) {
  this.username = username;
  this.userNum = userNum;
  this.deptName = deptName;
  this.year = year.toString();
  this.semester = semester.getName();
  this.scholarshipName = scholarshipName;
  this.paymentType = type.getName();
  this.amount = amount;
  this.confDate = confDate;
 }
}

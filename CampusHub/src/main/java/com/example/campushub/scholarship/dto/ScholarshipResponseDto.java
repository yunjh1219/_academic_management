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

@Getter
@NoArgsConstructor
public class ScholarshipResponseDto {

 private String username;
 private String userNum;
 private String deptName;
 @DateTimeFormat(pattern = "yyyy")
 private LocalDate year; //학년도
 private Semester semester; //학기
 private String scholarshipName; //장학금명
 private PaymentType type; //지급구분
 private int amount; //장학금액
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private LocalDate confDate; //확정일자

 @Builder
 @QueryProjection
 public ScholarshipResponseDto(String username, String userNum ,String deptName, LocalDate year, Semester semester, String scholarshipName, PaymentType type, int amount, LocalDate confDate ) {
  this.username = username;
  this.userNum = userNum;
  this.deptName = deptName;
  this.year = year;
  this.semester = semester;
  this.scholarshipName = scholarshipName;
  this.type = type;
  this.amount = amount;
  this.confDate = confDate;
 }
}

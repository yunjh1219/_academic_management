package com.example.campushub.scholarship.dto;


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
 private LocalDate year;
 private Enum  semester;
 private String scholarshipName;
 private Enum type;
 private int amount;
 @DateTimeFormat(pattern = "yyyy-MM-dd")
 private LocalDate confDate;

 @Builder
 @QueryProjection
 public ScholarshipResponseDto(String username, String userNum ,String deptName, LocalDate year, Enum semester, String scholarshipName, Enum type, int amount, LocalDate confDate ) {
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

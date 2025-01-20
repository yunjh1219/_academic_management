package com.home._ac_front.assign.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Assign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 과제 ID (자동 생성)

    private String courseName; // 강의명
    private String week; // 주차 (예: 1주차, 2주차 등)
    private String professorName; // 교수명
    private String content; // 과제 내용
    private LocalDate createDate; // 작성일

    @JsonFormat(pattern = "yyyy-MM-dd")  // 날짜 형식 지정
    private LocalDate dueDate; // 제출기한


}
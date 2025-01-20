package com.home._ac_front.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseTimeDTO {
    private String courseName;  // 강의명
    private String courseDay;   // 요일
    private int start;          // 시작 교시
    private int end;            // 끝나는 교시
}
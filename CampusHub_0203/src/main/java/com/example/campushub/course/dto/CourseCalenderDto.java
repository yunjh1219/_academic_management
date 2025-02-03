package com.example.campushub.course.dto;


import com.example.campushub.course.domain.CourseDay;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseCalenderDto {

    private String courseName;
    private String courseDay;
    private int startPeriod;
    private int endPeriod;


    @Builder
    @QueryProjection
    public CourseCalenderDto(String courseName, CourseDay courseDay, int startPeriod, int endPeriod) {
        this.courseName = courseName;
        this.courseDay = courseDay.getName();
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }
}

package com.example.campushub.course.dto;

import com.example.campushub.course.domain.CourseDay;
import com.example.campushub.course.domain.CourseDivision;
import com.example.campushub.course.domain.CourseGrade;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseResponseDto {
	private Long id;
	private String courseGrade;
	private String courseName;
	private String courseDivision;
	private int creditScore;
	private String professorName;
	private String courseRoom;
	private String courseDay;
	private int startPeriod;
	private int endPeriod;

	@Builder
	@QueryProjection
	public CourseResponseDto(Long id, CourseGrade courseGrade, String courseName, CourseDivision courseDivision, int creditScore,
		String professorName, String courseRoom, CourseDay courseDay, int startPeriod, int endPeriod) {
		this.id = id;
		this.courseGrade = courseGrade.getName();
		this.courseName = courseName;
		this.courseDivision = courseDivision.getName();
		this.creditScore = creditScore;
		this.professorName = professorName;
		this.courseRoom = courseRoom;
		this.courseDay = courseDay.getName();
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}
}

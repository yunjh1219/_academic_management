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
	private CourseGrade courseGrade;
	private String courseName;
	private CourseDivision courseDivision;
	private int creditScore;
	private String professorName;
	private String courseRoom;
	private CourseDay courseDay;
	private int startPeriod;
	private int endPeriod;

	@Builder
	@QueryProjection
	public CourseResponseDto(Long id, CourseGrade courseGrade, String courseName, CourseDivision courseDivision, int creditScore,
		String professorName, String courseRoom, CourseDay courseDay, int startPeriod, int endPeriod) {
		this.id = id;
		this.courseGrade = courseGrade;
		this.courseName = courseName;
		this.courseDivision = courseDivision;
		this.creditScore = creditScore;
		this.professorName = professorName;
		this.courseRoom = courseRoom;
		this.courseDay = courseDay;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}
}

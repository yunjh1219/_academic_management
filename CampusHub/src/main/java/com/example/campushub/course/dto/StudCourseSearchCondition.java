package com.example.campushub.course.dto;

import com.example.campushub.course.domain.CourseDivision;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudCourseSearchCondition {
	private CourseDivision division;
	private String deptName;
	private String courseName;

	@Builder
	public StudCourseSearchCondition(CourseDivision division, String deptName, String courseName) {
		this.division = division;
		this.deptName = deptName;
		this.courseName = courseName;
	}
}

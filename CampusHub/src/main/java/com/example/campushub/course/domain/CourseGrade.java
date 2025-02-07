package com.example.campushub.course.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseGrade {
	FiRST_GRADE("1학년"), SECOND_GRADE("2학년"), THIRD_GRADE("3학년"), FOURTH_GRADE("4학년");

	private final String name;

	public static CourseGrade of(String koreanName){
		if (koreanName.equals("1학년")){
			return FiRST_GRADE;
		} else if (koreanName.equals("2학년")) {
			return SECOND_GRADE;
		} else if (koreanName.equals("3학년")) {
			return THIRD_GRADE;
		} else return FOURTH_GRADE;
	}
}

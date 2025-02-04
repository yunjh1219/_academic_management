package com.example.campushub.course.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseDivision {
	//전공 필수, 전공 선택, 교양
	MAJOR_REQUIRED("전공 필수"), MAJOR_ELECTIVE("전공 선택"), GENERAL("교양");

	private final String name;

	public static CourseDivision of(String koreanName) {
		if (koreanName.equals("전공 필수")){
			return MAJOR_REQUIRED;
		} else if (koreanName.equals("전공 선택")) {
			return MAJOR_ELECTIVE;
		} else return GENERAL;
	}
}

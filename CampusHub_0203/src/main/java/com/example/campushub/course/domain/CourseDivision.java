package com.example.campushub.course.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseDivision {
	// 전공 필수, 전공 선택, 교양
	MAJOR_REQUIRED("전공 필수"), MAJOR_ELECTIVE("전공 선택"), GENERAL("교양");

	private final String name;

	// 문자열을 받아 해당하는 CourseDivision을 반환하는 메서드
	public static CourseDivision of(String division) {
		if (division == null) {
			return null;  // null이 들어오면 null 반환
		}

		for (CourseDivision courseDivision : values()) {
			if (courseDivision.name.equals(division)) {
				return courseDivision;  // 일치하는 값을 찾으면 반환
			}
		}

		throw new IllegalArgumentException("Unknown division: " + division);  // 일치하는 값이 없으면 예외 발생
	}
}
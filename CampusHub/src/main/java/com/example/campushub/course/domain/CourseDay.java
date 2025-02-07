package com.example.campushub.course.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseDay {
	Monday("월요일"), TuesDay("화요일"), WednesDay("수요일"), ThursDay("목요일"), Friday("금요일");

	private final String name;

	public static CourseDay of(String koreanName) {
		if (koreanName.equals("월요일")) {
			return Monday;
		} else if (koreanName.equals("화요일")) {
			return TuesDay;
		} else if (koreanName.equals("수요일")) {
			return WednesDay;
		} else if (koreanName.equals("목요일")) {
			return ThursDay;
		} else return Friday;
	}
}

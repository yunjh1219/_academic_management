package com.example.campushub.semsterschedule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Schedule {
	//수강신청
    COURSE_APPLY("수강신정");

	private final String message;

	public static Schedule of(String string) {
		if (string.equals("수강신청")){
			return COURSE_APPLY;
		}
		return null;
	}
}

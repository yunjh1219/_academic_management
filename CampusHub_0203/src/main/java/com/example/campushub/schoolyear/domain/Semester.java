package com.example.campushub.schoolyear.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Semester {
	first_semester("1학기"), second_semester("2학기");

	private final String name;


	public static Semester of(String number) {
		if (number == "1학기") return first_semester;
		else return second_semester;
	}

}

package com.example.campushub.schoolyear.domain;

public enum Semester {
	first_semester, second_semester;

	// TODO.
	public static Semester of(Long number) {
		if (number == 1) return first_semester;
		else return second_semester;
	}

}

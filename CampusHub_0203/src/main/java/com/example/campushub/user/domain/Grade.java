package com.example.campushub.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
	FIRST_GRADE("1학년"), SECOND_GRADE("2학년"), THIRD_GRADE("3학년"), FOURTH_GRADE("4학년");

	private final String message;
}

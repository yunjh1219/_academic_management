package com.example.campushub.nweek.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Week {
	FIRST("1주차"), SECOND("2주차"), THIRD("3주차"), FOURTH("4주차"), FIFTH("5주차"),
	SIXTH("6주차"), SEVENTH("7주차"), EIGHTH("8주차"), NINTH("9주차"), TENTH("10주차"),
	ELEVENTH("11주차"), TWELFTH("12주차"), THIRTEENTH("13주차"), FOURTEENTH("14주차"),
	FIFTEENTH("15주차"), SIXTEENTH("16주차");

	private final String name;
	}

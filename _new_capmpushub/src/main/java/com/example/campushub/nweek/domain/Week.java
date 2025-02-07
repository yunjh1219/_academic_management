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

	public static Week of(String koreanName) {
		if (koreanName.equals("1주차")) {
			return FIRST;
		} else if (koreanName.equals("2주차")) {
			return SECOND;
		} else if (koreanName.equals("3주차")) {
			return THIRD;
		} else if (koreanName.equals("4주차")) {
			return FOURTH;
		} else if (koreanName.equals("5주차")) {
			return FIFTH;
		} else if (koreanName.equals("6주차")) {
			return SIXTH;
		} else if (koreanName.equals("7주차")) {
			return SEVENTH;
		} else if (koreanName.equals("8주차")) {
			return EIGHTH;
		} else if (koreanName.equals("9주차")) {
			return NINTH;
		} else if (koreanName.equals("10주차")) {
			return TENTH;
		} else if (koreanName.equals("11주차")) {
			return ELEVENTH;
		} else if (koreanName.equals("12주차")) {
			return TWELFTH;
		} else if (koreanName.equals("13주차")) {
			return THIRTEENTH;
		} else if (koreanName.equals("14주차")) {
			return FOURTEENTH;
		} else if (koreanName.equals("15주차")) {
			return FIFTEENTH;
		} else {
			return SIXTEENTH;
		}
	}

	}

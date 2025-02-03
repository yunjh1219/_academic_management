package com.example.campushub.nweek.domain;

import com.example.campushub.scholarship.domain.PaymentType;
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

	// 주차 이름을 받아 해당 enum 값을 반환하는 메서드
	public static Week of(String weekName) {
		for (Week week : values()) {
			if (week.getName().equals(weekName)) {
				return week;  // 일치하는 주차 이름을 가진 enum 반환
			}
		}
		throw new IllegalArgumentException("Invalid week name: " + weekName);  // 유효하지 않은 주차 이름 처리
	}
}

package com.example.campushub.attendance.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendanceStatus {
	//출석, 결석, 지각, 조퇴,
	ATTENDANCE("출석"), ABSENCE("결석"), PERCEPTION("지각"), EARLY_DISMISSAL("조퇴");

	private final String koreanName;

	public static AttendanceStatus of(String koreanName) {
		if(koreanName.equals("출석")) {
			return ATTENDANCE;
		} else if (koreanName.equals("결석")) {
			return ABSENCE;
		} else if (koreanName.equals("지각")) {
			return PERCEPTION;
		} else return EARLY_DISMISSAL;
	}

}

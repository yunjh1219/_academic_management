package com.example.campushub.attendance.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendanceStatus {
	//출석, 결석, 지각, 조퇴,
	ATTENDANCE("출석"), ABSENCE("결석"), PERCEPTION("지각"), EARLYDISMISSAL("조퇴");

	private final String koreanName;

}

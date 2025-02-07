package com.example.campushub.studentassignment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubmitStatus {
	SUBMITTED("제출"), NOT_SUBMITTED("미제출");

	private final String message;

	public static SubmitStatus of(String koreanName){
		if (koreanName.equals("제출")){
			return SUBMITTED;
		} else return NOT_SUBMITTED;
	}
}

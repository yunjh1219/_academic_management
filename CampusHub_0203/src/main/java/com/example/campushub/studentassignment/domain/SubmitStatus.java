package com.example.campushub.studentassignment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubmitStatus {
	SUBMITTED("제출"), NOT_SUBMITTED("미제출");

	private final String message;
}

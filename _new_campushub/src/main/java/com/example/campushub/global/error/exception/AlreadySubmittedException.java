package com.example.campushub.global.error.exception;

public class AlreadySubmittedException extends ApiException {

	private static final String MESSAGE = "과제를 이미 제출하였습니다";

	public AlreadySubmittedException() {
		super(MESSAGE);
	}

	public AlreadySubmittedException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}

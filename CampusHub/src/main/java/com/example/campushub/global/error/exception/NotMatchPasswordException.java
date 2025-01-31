package com.example.campushub.global.error.exception;

public class NotMatchPasswordException extends ApiException {

	private static final String MESSAGE = "기존 비밀번호가 일치하지 않습니다.";

	public NotMatchPasswordException() {
		super(MESSAGE);
	}

	public NotMatchPasswordException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}

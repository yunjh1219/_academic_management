package com.example.campushub.global.error.exception;

public class DuplicateDayTimeException extends ApiException {

	private static final String MESSAGE = "시간이 겹칩니다.";

	public DuplicateDayTimeException() {
		super(MESSAGE);
	}

	public DuplicateDayTimeException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}

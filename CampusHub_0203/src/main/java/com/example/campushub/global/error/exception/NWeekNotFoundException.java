package com.example.campushub.global.error.exception;

public class NWeekNotFoundException extends ApiException {

	private static final String MESSAGE = "주차를 찾을 수 없습니다";

	public NWeekNotFoundException() {
		super(MESSAGE);
	}

	public NWeekNotFoundException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}

package com.example.campushub.global.error.exception;

public class InvalidDeptException extends ApiException {

	private static final String MESSAGE = "유효하지 않은 학과입니다";

	public InvalidDeptException() {
		super(MESSAGE);
	}

	public InvalidDeptException(String field, String message) {
		super(MESSAGE);
		addValidation(field, message);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}

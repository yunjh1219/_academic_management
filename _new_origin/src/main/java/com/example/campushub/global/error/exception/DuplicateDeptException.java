package com.example.campushub.global.error.exception;

public class DuplicateDeptException extends ApiException {

	private static final String MESSAGE = "겹치는 학과가 있습니다";

	public DuplicateDeptException() {
		super(MESSAGE);
	}

	public DuplicateDeptException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}

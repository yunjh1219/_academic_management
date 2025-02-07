package com.example.campushub.global.error.exception;

public class DuplicateUserNumException extends ApiException {

	private static final String MESSAGE = "중복된 학번입니다";

	public DuplicateUserNumException() {
		super(MESSAGE);
	}

	public DuplicateUserNumException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}

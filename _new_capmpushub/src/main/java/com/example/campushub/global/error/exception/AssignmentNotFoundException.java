package com.example.campushub.global.error.exception;

public class AssignmentNotFoundException extends ApiException {

	private static final String MESSAGE = "과제를 찾을 수 없습니다";

	public AssignmentNotFoundException() {
		super(MESSAGE);
	}

	public AssignmentNotFoundException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}

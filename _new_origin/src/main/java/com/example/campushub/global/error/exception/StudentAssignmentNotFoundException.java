package com.example.campushub.global.error.exception;

public class StudentAssignmentNotFoundException extends ApiException {

	private static final String MESSAGE = "학생과제를 찾을 수 없습니다";

	public StudentAssignmentNotFoundException() {
		super(MESSAGE);
	}

	public StudentAssignmentNotFoundException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}

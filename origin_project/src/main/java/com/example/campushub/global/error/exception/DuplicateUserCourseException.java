package com.example.campushub.global.error.exception;

public class DuplicateUserCourseException extends ApiException {

	private static final String MESSAGE = "이미 신청한 강의입니다.";

	public DuplicateUserCourseException() {
		super(MESSAGE);
	}

	public DuplicateUserCourseException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}

package com.example.campushub.global.error.exception;

public class DuplicateCourseException extends ApiException {

	private static final String MESSAGE = "겹치는 강의가 있습니다";

	public DuplicateCourseException() {
		super(MESSAGE);
	}

	public DuplicateCourseException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}

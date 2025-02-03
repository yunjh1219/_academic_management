package com.example.campushub.global.error.exception;

public class CourseNotFoundException extends ApiException {

	private static final String MESSAGE = "강의를 찾을 수 없습니다";

	public CourseNotFoundException() {
		super(MESSAGE);
	}

	public CourseNotFoundException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}

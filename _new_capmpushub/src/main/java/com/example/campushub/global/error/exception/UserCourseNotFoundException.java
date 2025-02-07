package com.example.campushub.global.error.exception;

public class UserCourseNotFoundException extends ApiException {

	private static final String MESSAGE = "사용자강의를 찾을 수 없습니다";

	public UserCourseNotFoundException() {
		super(MESSAGE);
	}

	public UserCourseNotFoundException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}

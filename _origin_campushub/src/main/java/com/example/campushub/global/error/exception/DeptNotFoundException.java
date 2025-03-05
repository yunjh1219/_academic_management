package com.example.campushub.global.error.exception;

public class DeptNotFoundException extends ApiException {

	private static final String MESSAGE = "학과를 찾을 수 없습니다";

	public DeptNotFoundException() {
		super(MESSAGE);
	}

	public DeptNotFoundException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}

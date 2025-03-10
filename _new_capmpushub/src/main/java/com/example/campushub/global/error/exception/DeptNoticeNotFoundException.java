package com.example.campushub.global.error.exception;

public class DeptNoticeNotFoundException extends ApiException {

	private static final String MESSAGE = "학과 공지사항를 찾을 수 없습니다";

	public DeptNoticeNotFoundException() {
		super(MESSAGE);
	}

	public DeptNoticeNotFoundException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 404;
	}
}

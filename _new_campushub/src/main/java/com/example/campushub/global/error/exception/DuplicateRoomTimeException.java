package com.example.campushub.global.error.exception;

public class DuplicateRoomTimeException extends ApiException {

	private static final String MESSAGE = "시간이 겹쳐 강의실을 사용할 수 없습니다";

	public DuplicateRoomTimeException() {
		super(MESSAGE);
	}

	public DuplicateRoomTimeException(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 409;
	}
}

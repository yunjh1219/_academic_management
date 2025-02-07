package com.example.campushub.global.error.exception;

public class IsNotPendingStatusException extends ApiException {

	private static final String MESSAGE = "대기중인 상태가 아닙니다";

	public IsNotPendingStatusException() {
		super(MESSAGE);
	}

	public IsNotPendingStatusException(String field, String message) {
		super(MESSAGE);
		addValidation(field, message);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}

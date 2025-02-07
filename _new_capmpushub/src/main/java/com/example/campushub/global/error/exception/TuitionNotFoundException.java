package com.example.campushub.global.error.exception;

public class TuitionNotFoundException extends ApiException {


    private static final String MESSAGE = "등록 정보를 찾을 수 없습니다";

    public TuitionNotFoundException() {
        super(MESSAGE);
    }

    public TuitionNotFoundException(String message,String fieldName) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 404; }
}

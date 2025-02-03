package com.example.campushub.global.error.exception;

public class SchoolyearNotFoundException extends ApiException {

    private static final String MESSAGE = "학년도와 학기를 찾을 수 없습니다";


    public SchoolyearNotFoundException() {
        super(MESSAGE);
    }

    public SchoolyearNotFoundException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {return 404;}
}

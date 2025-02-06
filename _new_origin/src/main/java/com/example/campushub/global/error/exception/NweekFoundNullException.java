package com.example.campushub.global.error.exception;

public class NweekFoundNullException extends ApiException {

    private static final String MESSAGE = "조회된 리스트에 NULL값이 있습니다";

    public NweekFoundNullException() {super(MESSAGE);}

    public NweekFoundNullException(String fieldName,String message) {
        super(message);
        addValidation(fieldName,message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}

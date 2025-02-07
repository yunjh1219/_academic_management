package com.example.campushub.global.error.exception;

public class StudentStatusNotMatchExecption extends ApiException {

    private static final String MESSAGE = "재학 중이거나 휴학 중이 아닙니다";

    public StudentStatusNotMatchExecption() {super(MESSAGE);}

    public StudentStatusNotMatchExecption(String fieldName,String message) {
        super(MESSAGE);
        addValidation(fieldName,message);
    }


    @Override
    public int getStatusCode() { return 404; }
}

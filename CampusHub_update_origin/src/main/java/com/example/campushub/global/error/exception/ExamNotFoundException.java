package com.example.campushub.global.error.exception;

public class ExamNotFoundException extends ApiException {

    private static final String MESSAGE = "Exam not found";

    public ExamNotFoundException() { super(MESSAGE); }

    public ExamNotFoundException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }
    @Override
    public int getStatusCode() {return 404;}
}

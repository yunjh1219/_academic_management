package com.example.campushub.global.error.exception;

public class CourseNameOrweekNotFoundException extends ApiException {

  private static final String MESSAGE = "강의명과 주차는 필수 입력 값입니다.";
    public CourseNameOrweekNotFoundException() {
        super(MESSAGE);
    }

    public CourseNameOrweekNotFoundException(String fieldName,String message) {
      super(MESSAGE);
      addValidation(fieldName,message);
    }

  @Override
  public int getStatusCode() {
    return 404;
  }
}

package com.example.campushub.global.error.exception;

public class AttendanceConditionNotSetException extends ApiException {

  private static final String MESSAGE = "강의명과 주차 중 하나를 설정하지 않았습니다";

    public AttendanceConditionNotSetException() {
        super(MESSAGE);
    }

    public AttendanceConditionNotSetException(String fieldName,String message) {
    super(MESSAGE);
    addValidation(fieldName,message);
    }


  @Override
  public int getStatusCode() {
    return 404;
  }
}

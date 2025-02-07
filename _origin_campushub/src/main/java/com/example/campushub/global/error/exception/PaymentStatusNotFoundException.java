package com.example.campushub.global.error.exception;

public class PaymentStatusNotFoundException extends ApiException {

  private static final String MESSAGE = "납부 여부를 찾을 수 없습니다";

  public PaymentStatusNotFoundException() {
    super(MESSAGE);
  }

  public PaymentStatusNotFoundException(String fieldName,String message) {
    super(MESSAGE);
    addValidation(fieldName,message);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}

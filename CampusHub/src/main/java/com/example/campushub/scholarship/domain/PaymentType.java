package com.example.campushub.scholarship.domain;

public enum PaymentType {
	POST_PAYMENT,
	OTHER_PAYMENT;

	// 문자열을 Enum으로 변환하는 메소드
	public static PaymentType fromString(String type) {
		switch (type) {
			case "POST_PAYMENT":
				return POST_PAYMENT;
			case "OTHER_PAYMENT":
				return OTHER_PAYMENT;
			default:
				throw new IllegalArgumentException("Unknown payment type: " + type);
		}
	}
}
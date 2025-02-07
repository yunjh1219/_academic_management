package com.example.campushub.scholarship.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentType {

	PRE_PAYMENT("사전감면"), POST_PAYMENT("사후감면");

	private final String name;

	public static PaymentType fromName(String key) {
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType.name.equals(key)) {
				return paymentType;
			}
		}
		return null;
	}
	public static PaymentType of(String paymentType) {
		if (paymentType.equals("사전감면")) return PRE_PAYMENT;
		else return POST_PAYMENT;
	}

}

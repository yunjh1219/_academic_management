package com.example.campushub.scholarship.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentType {

	PRE_PAYMENT("PRE_PAYMENT"), POST_PAYMENT("POST_PAYMENT");

	private final String key;

	public static PaymentType fromKey(String key) {
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType.key.equals(key)) {
				return paymentType;
			}
		}
		return null;
	}

}

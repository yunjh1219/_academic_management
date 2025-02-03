package com.example.campushub.scholarship.domain;

import com.example.campushub.schoolyear.domain.Semester;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentType {

	PRE_PAYMENT("사전감면"), POST_PAYMENT("사후지급");

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
		if (paymentType == "사전감면") return PRE_PAYMENT;
		else return POST_PAYMENT;
	}

}

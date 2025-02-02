package com.example.campushub.usertuition.domain;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum PaymentStatus {
    //미납, 대기 ,완료
    NONE, WAITING, SUCCESS;

    public static PaymentStatus of(String status) {
        return PaymentStatus.valueOf(status.toUpperCase(Locale.ENGLISH));
    }
}

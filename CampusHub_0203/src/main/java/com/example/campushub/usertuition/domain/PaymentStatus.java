package com.example.campushub.usertuition.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    //미납, 대기 ,완료
    NONE("미납"), WAITING("대기"), SUCCESS("완료");

    private final String name;

    public static PaymentStatus of(String status) {
        return PaymentStatus.valueOf(status);
    }
}

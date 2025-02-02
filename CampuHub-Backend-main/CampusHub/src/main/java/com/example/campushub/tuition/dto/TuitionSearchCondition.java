package com.example.campushub.tuition.dto;


import com.example.campushub.usertuition.domain.PaymentStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TuitionSearchCondition {

    private String deptName;
    private String userNum;
    private PaymentStatus paymentStatus;

    @Builder
    public TuitionSearchCondition(String deptName, String userNum, PaymentStatus paymentStatus) {
        this.deptName = deptName;
        this.userNum = userNum;
        this.paymentStatus = paymentStatus;
    }
}

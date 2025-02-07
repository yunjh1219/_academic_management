package com.example.campushub.scholarship.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScholarshipSearchCondition {

    private String deptName;
    private String userNum;

    @Builder
    public ScholarshipSearchCondition(String deptName, String userNum) {
        this.deptName = deptName;
        this.userNum = userNum;
    }

    public static ScholarshipSearchCondition of(String deptName, String userNum) {
        return ScholarshipSearchCondition.builder()
                .deptName(deptName)
                .userNum(userNum)
                .build();
    }

}

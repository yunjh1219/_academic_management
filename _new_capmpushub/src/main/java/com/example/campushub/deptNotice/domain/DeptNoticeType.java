package com.example.campushub.deptNotice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeptNoticeType {

    UNDERGRADUATE("학부"), SCHOLARSHIP("장학"), COMMON("공통");

    private final String name;

    public static DeptNoticeType of(String koreanName){
        if (koreanName.equals("학부")){
            return UNDERGRADUATE;
        } else if (koreanName.equals("장학")) {
            return SCHOLARSHIP;
        }
        else return COMMON;
    }
}

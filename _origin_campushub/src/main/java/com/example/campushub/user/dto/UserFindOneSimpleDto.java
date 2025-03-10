package com.example.campushub.user.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFindOneSimpleDto {

    private String userName;
    private String deptName;
    private String userNum;


    @Builder
    public UserFindOneSimpleDto(String userName, String deptName, String userNum) {
        this.userName = userName;
        this.deptName = deptName;
        this.userNum = userNum;
    }



}

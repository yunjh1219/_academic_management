package com.example.campushub.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserInfoDto {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @NotBlank(message = "연락처를 입력해주세요")
    private String phone;
    @NotBlank(message = "주소를 입력해주세요")
    private String address;



    @Builder
    public UpdateUserInfoDto(String email, String phone, String address) {
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}

package com.example.campushub.dept.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class DeptCreateDto {
    @NotBlank(message = "학과명을 입력해주세요")
    private String deptName;

    @Builder
    public DeptCreateDto(String deptName) {
        this.deptName = deptName;
    }
}

package com.example.campushub.dept.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeptResponseDto {
    private Long Id;
    private String deptName;

    @Builder
    @QueryProjection
    public DeptResponseDto(Long Id, String deptName) {
        this.Id = Id;
        this.deptName = deptName;
    }
}

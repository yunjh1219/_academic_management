package com.example.campushub.dept.controller;

import com.example.campushub.dept.dto.DeptCreateDto;
import com.example.campushub.dept.service.DeptService;
import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.user.dto.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    @PostMapping("/api/admin/dept")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> createDept(@Login LoginUser loginUser, @RequestBody @Valid DeptCreateDto createDto) {

        deptService.createDept(loginUser, createDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("학과정보 생성 성공")
                .build();
    }
}

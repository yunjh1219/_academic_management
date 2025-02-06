package com.example.campushub.dept.controller;

import com.example.campushub.dept.dto.DeptCreateDto;
import com.example.campushub.dept.dto.DeptEditDto;
import com.example.campushub.dept.dto.DeptResponseDto;
import com.example.campushub.dept.service.DeptService;
import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.user.dto.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    //관리자 학과 생성
    @PostMapping("/api/admin/dept")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> createDept(@Login LoginUser loginUser, @RequestBody @Valid DeptCreateDto createDto) {

        deptService.createDept(loginUser, createDto);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("학과정보 생성 성공")
                .build();
    }

    //관리자 학과 조회
    @GetMapping("/api/admin/dept/all")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<DeptResponseDto>> findAllDept(@Login LoginUser loginUser) {
        return SuccessResponse.<List<DeptResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message("학과 조회 성공")
                .data(deptService.findAllDept(loginUser))
                .build();
    }

    //관리자 학과명 수정
    @PatchMapping("/api/admin/dept/{deptId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> editDept(@Login LoginUser loginUser, @PathVariable Long deptId,
                                          @RequestBody @Valid DeptEditDto editDto){
        deptService.editDept(loginUser, editDto, deptId);

        return SuccessResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("학과명 수정 성공")
                .build();
    }

}

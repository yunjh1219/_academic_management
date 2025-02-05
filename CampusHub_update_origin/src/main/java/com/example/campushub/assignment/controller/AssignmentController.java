package com.example.campushub.assignment.controller;


import com.example.campushub.assignment.dto.AssignmentCreateRequest;
import com.example.campushub.assignment.dto.AssignmentFindAllResponse;
import com.example.campushub.assignment.dto.AssignmentResponse;
import com.example.campushub.assignment.dto.AssignmentSearchCondition;
import com.example.campushub.assignment.service.AssignmentService;
import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.user.dto.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;


    //과제 등록
    @PostMapping("/api/professor/assignment/create")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> createAssignment(@Login LoginUser loginUser, @RequestBody AssignmentCreateRequest request){
        assignmentService.createAssignment(loginUser, request);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("과제 등록 성공")
                .build();
    }

    //과제 조회 (학생)
    @GetMapping("/api/student/assignment/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<AssignmentFindAllResponse>> getAllAssignments(@Login LoginUser loginUser, AssignmentSearchCondition cond){
        return SuccessResponse.<List<AssignmentFindAllResponse>>builder()
                .status(200)
                .data(assignmentService.findAllByCondition(loginUser,cond))
                .message("과제 조회 성공")
                .build();

    }

    //과제 단건 조회
    @GetMapping("/api/student/assignment/condition/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<AssignmentResponse> getAssignment(@Login LoginUser loginUser, @PathVariable Long assignmentId){
        return SuccessResponse.<AssignmentResponse>builder()
                .status(200)
                .data(assignmentService.findAssignment(loginUser,assignmentId))
                .message("과제 단건 조회 성공")
                .build();
    }
}

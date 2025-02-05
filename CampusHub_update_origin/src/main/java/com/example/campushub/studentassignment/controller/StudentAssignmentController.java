package com.example.campushub.studentassignment.controller;

import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.Login;
import com.example.campushub.studentassignment.dto.StudentAssigmentSearchCondition;
import com.example.campushub.studentassignment.dto.StudentAssignFindOneDto;
import com.example.campushub.studentassignment.dto.StudentAssignmentResponse;
import com.example.campushub.studentassignment.dto.StudentAssignmentSubmitDto;
import com.example.campushub.studentassignment.service.StudentAssignmentService;
import com.example.campushub.user.dto.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentAssignmentController {

    private final StudentAssignmentService studentAssignmentService;

    //과제 제출(학생)
    @PostMapping("/api/student/assignment/{assignmentId}/submit")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> submitAssignment(@Login LoginUser loginUser, @PathVariable Long assignmentId, @RequestBody StudentAssignmentSubmitDto submitDto) {
        studentAssignmentService.SubmitStudentAssignment(loginUser,submitDto,assignmentId);
        return SuccessResponse.<Void>builder()
                .status(200)
                .message("과제 제출 성공")
                .build();
    }


    //학생 과제 전체 조회
    @GetMapping("/api/professor/assignment/condition")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<List<StudentAssignmentResponse>> getAllStudentsAssignment(@Login LoginUser loginUser, StudentAssigmentSearchCondition cond){
        return SuccessResponse.<List<StudentAssignmentResponse>>builder()
                .status(200)
                .data(studentAssignmentService.getAllStudentAssignment(loginUser,cond))
                .message("학생이 제출한 과제 조회 성공")
                .build();
    }

    //학생 과제 단건 조회
    @GetMapping("/api/professor/assignment/condition/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<StudentAssignFindOneDto> getStudentAssignment(@Login LoginUser loginUser, @PathVariable Long assignmentId){
        return SuccessResponse.<StudentAssignFindOneDto>builder()
                .status(200)
                .data(studentAssignmentService.getStudentAssignment(loginUser,assignmentId))
                .message("학생 과제 단건 조회 성공")
                .build();
    }

    @PostMapping("/api/professor/assignment/condition/{assignmentId}/setscore")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> setAssignScore(@Login LoginUser loginUser,@PathVariable Long studentAssignId, @PathVariable int score){
        studentAssignmentService.setStudentAssignScore(loginUser,studentAssignId,score);
        return SuccessResponse.<Void>builder()
                .status(200)
                .message("점수 기입 성공")
                .build();
    }

}

package com.example.campushub.departmentnotice.controller;

import com.example.campushub.course.dto.CourseResponseDto;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeRequestDto;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeResponseDto;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeUpdateRequestDto;
import com.example.campushub.departmentnotice.service.DepartmentNoticeService;
import com.example.campushub.global.security.Login;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeSearchCondition; // 변경된 검색 조건 DTO
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.usercourse.dto.UserCourseResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class DepartmentNoticeController {

    private final DepartmentNoticeService departmentNoticeService;

    public DepartmentNoticeController(DepartmentNoticeService departmentNoticeService) {
        this.departmentNoticeService = departmentNoticeService;
    }

    // 학과 공지사항 리스트
    @GetMapping("/api/department-notices")
    public ResponseEntity<Page<DepartmentNoticeResponseDto>> getDepartmentNotices(
            @Login LoginUser loginUser,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "title") String filter,
            @RequestParam(required = false) String keyword) {

        DepartmentNoticeSearchCondition condition = DepartmentNoticeSearchCondition.builder()
                .filter(filter)
                .keyword(keyword)
                .build();

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<DepartmentNoticeResponseDto> notices = departmentNoticeService.getDepartmentNoticesForUser(loginUser, condition, pageRequest);

        return ResponseEntity.ok(notices);
    }

    // 학과 공지사항 세부 조회
    @GetMapping("/api/department-notices/{id}")
    public ResponseEntity<DepartmentNoticeResponseDto> getDepartmentNoticeById(
            @PathVariable Long id,
            @Login LoginUser loginUser) {

        DepartmentNoticeResponseDto notice = departmentNoticeService.getDepartmentNoticeById(id, loginUser);
        return ResponseEntity.ok(notice);
    }

    // 학과 공지사항 작성
    @PostMapping("/api/department-notices/create")
    public ResponseEntity<Long> createDepartmentNotice(
            @RequestBody @Valid DepartmentNoticeRequestDto requestDto,
            @Login LoginUser loginUser) {

        // 로그인한 유저의 타입이 PROFESSOR인지 확인
        if (!loginUser.getType().equals(Type.PROFESSOR)) {
            throw new AccessDeniedException("권한이 없습니다. 공지사항 작성은 교수만 가능합니다.");
        }

        departmentNoticeService.createDepartmentNotice(requestDto, loginUser);

        // 공지사항 리스트 페이지로 리다이렉트
        URI redirectUri = URI.create("/department-notices");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
    }

    // 학과 공지사항 작성 시 공지할 강의 선택 (드롭다운 / 프론트에서 js 작업 필요)
    @GetMapping("/api/department-notices/courses")
    public ResponseEntity<List<UserCourseResponseDto>> getCoursesForProfessor(@Login LoginUser loginUser) {
        List<UserCourseResponseDto> courses = departmentNoticeService.getCoursesByProfessor(loginUser.getUserNum());
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/api/department-notices/edit/{id}")
    public ResponseEntity<DepartmentNoticeResponseDto> editDepartmentNotice(
            @PathVariable Long id,
            @Login LoginUser loginUser) {

        // 공지사항 데이터 조회
        DepartmentNoticeResponseDto notice = departmentNoticeService.getDepartmentNoticeById(id, loginUser);

        return ResponseEntity.ok(notice);
    }

    // 학과 공지사항 수정
    @PutMapping("/api/department-notices/update/{id}")
    public ResponseEntity<Void> updateDepartmentNotice(
            @PathVariable Long id,
            @RequestBody @Valid DepartmentNoticeUpdateRequestDto updateRequestDto,
            @Login LoginUser loginUser) {

        // 로그인한 유저의 타입이 PROFESSOR인지 확인
        if (!loginUser.getType().equals(Type.PROFESSOR)) {
            throw new AccessDeniedException("권한이 없습니다. 공지사항 수정은 교수만 가능합니다.");
        }

        departmentNoticeService.updateDepartmentNotice(id, updateRequestDto, loginUser);

        // 공지사항 리스트 페이지로 리다이렉트
        URI redirectUri = URI.create("/department-notices");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
    }

    // 학과 공지사항 삭제
    @DeleteMapping("/api/department-notices/delete/{id}")
    public ResponseEntity<Void> deleteDepartmentNotice(
            @PathVariable Long id,
            @Login LoginUser loginUser) {

        // 로그인한 유저의 타입이 PROFESSOR인지 확인
        if (!loginUser.getType().equals(Type.PROFESSOR)) {
            throw new AccessDeniedException("권한이 없습니다. 공지사항 삭제은 교수만 가능합니다.");
        }

        departmentNoticeService.deleteDepartmentNotice(id, loginUser);
        return ResponseEntity.noContent().build();
    }

}

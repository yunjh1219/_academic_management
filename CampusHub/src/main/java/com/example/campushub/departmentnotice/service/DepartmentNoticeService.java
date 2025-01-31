package com.example.campushub.departmentnotice.service;

import com.example.campushub.course.dto.CourseResponseDto;
import com.example.campushub.departmentnotice.domain.DepartmentNotice;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeRequestDto;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeResponseDto;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeSearchCondition;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeUpdateRequestDto;
import com.example.campushub.departmentnotice.repository.DepartmentNoticeRepository;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.usercourse.domain.UserCourse;
import com.example.campushub.usercourse.dto.UserCourseResponseDto;
import com.example.campushub.usercourse.repository.UserCourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentNoticeService {

    private final DepartmentNoticeRepository departmentNoticeRepository;
    private final UserCourseRepository userCourseRepository;
    private final UserRepository userRepository;

    // 학과 공지사항 리스트 조회
    public Page<DepartmentNoticeResponseDto> getDepartmentNoticesForUser(LoginUser loginUser, DepartmentNoticeSearchCondition condition, Pageable pageable) {
        return departmentNoticeRepository.findDepartmentNoticesForUser(loginUser.getUserNum(), condition, pageable);
    }

    // 학과 공지사항 세부 조회
    public DepartmentNoticeResponseDto getDepartmentNoticeById(Long id, LoginUser loginUser) {
        return departmentNoticeRepository.findByIdAndUser(id, loginUser.getUserNum())
                .orElseThrow(() -> new EntityNotFoundException("해당 공지사항을 찾을 수 없습니다."));
    }

    // 학과 공지사항 작성
    public void createDepartmentNotice(DepartmentNoticeRequestDto requestDto, LoginUser loginUser) {
        // 로그인한 유저의 타입 확인
        if (!loginUser.getType().equals(Type.PROFESSOR)) {
            throw new AccessDeniedException("권한이 없습니다. 공지사항 작성은 교수만 가능합니다.");
        }

        // UserCourse 확인
        UserCourse userCourse = userCourseRepository.findById(requestDto.getUserCourseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 UserCourse ID입니다."));

        // 작성 권한 확인 (로그인한 사용자가 강의 개설자인지 검증)
        if (!userCourse.getCourse().getUser().getUserNum().equals(loginUser.getUserNum())) {
            throw new IllegalArgumentException("해당 강의의 공지사항을 작성할 권한이 없습니다.");
        }

        // 공지사항 생성
        DepartmentNotice notice = DepartmentNotice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .userCourse(userCourse)
                .author(userRepository.findByUserNum(loginUser.getUserNum())
                        .orElseThrow(() -> new IllegalArgumentException("User not found with userNum: " + loginUser.getUserNum())))
                .build();

        departmentNoticeRepository.save(notice);
    }

    // 작성할 공지의 강의 선택
    public List<UserCourseResponseDto> getCoursesByProfessor(String userNum) {
        return userCourseRepository.findAllByUser_UserNum(userNum).stream()
                .map(userCourse -> UserCourseResponseDto.builder()
                        .userCourseId(userCourse.getId())
                        .courseName(userCourse.getCourse().getCourseName())
                        .build())
                .collect(Collectors.toList());
    }

    // 학과 공지사항 수정
    public void updateDepartmentNotice(Long id, DepartmentNoticeUpdateRequestDto updateRequestDto, LoginUser loginUser) {
        DepartmentNotice notice = departmentNoticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 공지사항을 찾을 수 없습니다."));

        // 로그인한 사용자가 작성자인지 확인
        if (!notice.getAuthor().getUserNum().equals(loginUser.getUserNum())) {
            throw new AccessDeniedException("권한이 없습니다. 해당 공지사항을 수정할 수 없습니다.");
        }

        // 공지사항 수정
        notice.update(updateRequestDto.getTitle(), updateRequestDto.getContent());
    }

    // 학과 공지사항 삭제
    public void deleteDepartmentNotice(Long id, LoginUser loginUser) {
        // 공지사항 조회
        DepartmentNotice notice = departmentNoticeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("공지사항을 찾을 수 없습니다."));

        // 권한 확인
        if (!notice.getAuthor().getUserNum().equals(loginUser.getUserNum())) {
            throw new AccessDeniedException("공지사항 삭제 권한이 없습니다.");
        }

        // 삭제
        departmentNoticeRepository.delete(notice);
    }

}

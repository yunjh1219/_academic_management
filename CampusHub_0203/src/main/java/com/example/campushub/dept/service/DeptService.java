package com.example.campushub.dept.service;

import com.example.campushub.course.repository.CourseRepository;
import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.dto.DeptCreateDto;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.global.error.exception.DuplicateDeptException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeptService {

    private final DeptRepository deptRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    //부서 정보 등록
    @Transactional
    public void createDept(LoginUser loginUser, DeptCreateDto CreateDto) {
        userRepository.findByUserNumAndType(loginUser.getUserNum(),loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        //부서 중복 조건
        if (deptRepository.existsByDeptName(CreateDto.getDeptName())) {
            throw new DuplicateDeptException();
        }

        Dept dept = new Dept(CreateDto.getDeptName());

        deptRepository.save(dept);
    }
}

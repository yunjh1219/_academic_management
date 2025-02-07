package com.example.campushub.dept.service;

import com.example.campushub.course.repository.CourseRepository;
import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.dto.DeptCreateDto;
import com.example.campushub.dept.dto.DeptEditDto;
import com.example.campushub.dept.dto.DeptResponseDto;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.global.error.exception.DeptNotFoundException;
import com.example.campushub.global.error.exception.DuplicateDeptException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    //부서 전체 조회
    public List<DeptResponseDto> findAllDept(LoginUser loginUser) {
        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        return deptRepository.findAllDept();
    }

    @Transactional
    public void editDept(LoginUser loginUser, DeptEditDto editDto,Long deptId) {
        userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.PROFESSOR)
                .orElseThrow(UserNotFoundException::new);

        Dept dept = deptRepository.findById(deptId)
                .orElseThrow(DeptNotFoundException::new);

        dept.edit(editDto.getDeptName());

    }

}

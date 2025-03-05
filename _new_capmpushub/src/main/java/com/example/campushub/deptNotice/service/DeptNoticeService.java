package com.example.campushub.deptNotice.service;


import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.deptNotice.domain.DeptNotice;
import com.example.campushub.deptNotice.dto.DeptNoticeCreateRequestDto;
import com.example.campushub.deptNotice.dto.DeptNoticeEditDto;
import com.example.campushub.deptNotice.dto.DeptNoticeFindAllResponseDto;
import com.example.campushub.deptNotice.dto.DeptNoticeSearchCondition;
import com.example.campushub.deptNotice.repository.DeptNoticeRepository;
import com.example.campushub.global.error.exception.DeptNotFoundException;
import com.example.campushub.global.error.exception.DeptNoticeNotFoundException;
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
public class DeptNoticeService {

    private final DeptNoticeRepository deptNoticeRepository;
    private final UserRepository userRepository;
    private final DeptRepository deptRepository;

    //학과 공지사항 생성
    @Transactional
    public void createDeptNotice(LoginUser loginUser, DeptNoticeCreateRequestDto createDto) {

        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        Dept dept = deptRepository.findByDeptName(createDto.getDeptName())
                .orElseThrow(DeptNotFoundException::new);

        DeptNotice deptNotice = createDto.toEntity(user, dept);

        deptNoticeRepository.save(deptNotice);
    }

    //학과 공지사항 수정
    @Transactional
    public void editDeptNotice(LoginUser loginUser, Long deptNoticeId, DeptNoticeEditDto editDto){
        userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.ADMIN)
                .orElseThrow(UserNotFoundException::new);

        DeptNotice deptNotice = deptNoticeRepository.findById(deptNoticeId)
                .orElseThrow(DeptNoticeNotFoundException::new); //수정하려는 학과 공지사항 존재 여부 확인

        Dept dept = deptRepository.findByDeptName(editDto.getDeptName())
                .orElseThrow(DeptNotFoundException::new); //수정 요청을 보낸 학과명의 존재 여부 확인

        deptNotice.edit(editDto, dept);
    }

    //학과 공지사항 삭제
    @Transactional
    public void deletDeptNoticeByDeptNoticeId(LoginUser loginUser, Long deptNoticeId){

        userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        DeptNotice deptNotice = deptNoticeRepository.findById(deptNoticeId)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 공지사항을 찾을 수 없습니다."));

        deptNoticeRepository.delete(deptNotice);
    }

    //학과 공지사항 전체 조회 + 컨디션
    public List<DeptNoticeFindAllResponseDto> findAllByDeptNoticeCondition(LoginUser loginUser, DeptNoticeSearchCondition cond){
        if (loginUser != null) {
            userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                    .orElseThrow(UserNotFoundException::new);
        }

        return deptNoticeRepository.findAllByDeptNoticeCondition(cond);
    }

}

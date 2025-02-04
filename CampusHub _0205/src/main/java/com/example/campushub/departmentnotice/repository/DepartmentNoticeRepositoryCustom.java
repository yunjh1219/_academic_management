package com.example.campushub.departmentnotice.repository;

import com.example.campushub.departmentnotice.dto.DepartmentNoticeResponseDto;
import com.example.campushub.departmentnotice.dto.DepartmentNoticeSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartmentNoticeRepositoryCustom {

    Page<DepartmentNoticeResponseDto> findDepartmentNoticesForUser(String userNum, DepartmentNoticeSearchCondition condition, Pageable pageable);

    Optional<DepartmentNoticeResponseDto> findByIdAndUser(Long id, String userNum);

}

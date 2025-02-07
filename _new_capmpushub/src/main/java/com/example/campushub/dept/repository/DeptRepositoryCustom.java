package com.example.campushub.dept.repository;

import com.example.campushub.dept.dto.DeptResponseDto;

import java.util.List;

public interface DeptRepositoryCustom {

    List<DeptResponseDto>findAllDept();
}

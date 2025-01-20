package com.example.campushub.scholarship.repository;

import com.example.campushub.scholarship.dto.ScholarshipResponseDto;
import com.example.campushub.scholarship.dto.ScholarshipSearchCondition;
import com.example.campushub.user.domain.User;

import java.util.List;

public interface ScholarshipRepositoryCustom {

    List<ScholarshipResponseDto> findAllByCondition(ScholarshipSearchCondition cond);




}

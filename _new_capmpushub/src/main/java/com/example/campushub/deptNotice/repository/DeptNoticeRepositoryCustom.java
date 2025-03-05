package com.example.campushub.deptNotice.repository;

import com.example.campushub.deptNotice.dto.DeptNoticeFindAllResponseDto;
import com.example.campushub.deptNotice.dto.DeptNoticeSearchCondition;

import java.util.List;

public interface DeptNoticeRepositoryCustom {

    List<DeptNoticeFindAllResponseDto> findAllByDeptNoticeCondition(DeptNoticeSearchCondition cond);


}

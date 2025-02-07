package com.example.campushub.dept.repository;


import com.example.campushub.dept.dto.DeptResponseDto;
import com.example.campushub.dept.dto.QDeptResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.campushub.dept.domain.QDept.dept;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeptRepositoryImpl implements DeptRepositoryCustom {


    private final JPAQueryFactory queryFactory;




    @Override
    public List<DeptResponseDto> findAllDept() {
      return queryFactory.select(new QDeptResponseDto(
              dept.id,
              dept.deptName
      ))
              .from(dept)
              .fetch();
    }
}
package com.example.campushub.departmentnotice.repository;

import com.example.campushub.departmentnotice.domain.DepartmentNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentNoticeRepository extends JpaRepository<DepartmentNotice, Long>, DepartmentNoticeRepositoryCustom {
}

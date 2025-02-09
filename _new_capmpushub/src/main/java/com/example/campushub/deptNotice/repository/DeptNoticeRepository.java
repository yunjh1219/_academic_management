package com.example.campushub.deptNotice.repository;

import com.example.campushub.deptNotice.domain.DeptNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptNoticeRepository extends JpaRepository<DeptNotice, Long>, DeptNoticeRepositoryCustom {
}

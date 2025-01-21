package com.example.campushub.dept.repository;

import java.util.Optional;

import com.example.campushub.dept.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Long> {
	// Optional<Dept> findByName(String deptName);

	boolean existsByDeptName(String deptName);

	Optional<Dept> findByDeptName(String deptName);

}

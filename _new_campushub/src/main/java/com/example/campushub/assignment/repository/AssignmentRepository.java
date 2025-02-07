package com.example.campushub.assignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.campushub.assignment.domain.Assignment;
import com.example.campushub.assignment.dto.AssignmentResponse;

public interface AssignmentRepository extends JpaRepository<Assignment, Long>, AssignmentRepositoryCustom {
}

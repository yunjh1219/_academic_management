package com.example.campushub.assignment.repository;

import java.util.List;
import java.util.Optional;

import com.example.campushub.assignment.dto.AssignmentFindAllResponse;
import com.example.campushub.assignment.dto.AssignmentResponse;
import com.example.campushub.assignment.dto.AssignmentSearchCondition;

public interface AssignmentRepositoryCustom {

	List<AssignmentFindAllResponse> findAllAssigmentByCond(AssignmentSearchCondition condition, List<String> courseNames);
	Optional<AssignmentResponse> getAssignmentById(Long id);
}

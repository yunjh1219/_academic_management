package com.example.campushub.studentassignment.repository;

import java.util.List;
import java.util.Optional;

import com.example.campushub.studentassignment.dto.StudentAssigmentSearchCondition;
import com.example.campushub.studentassignment.dto.StudentAssignFindOneDto;
import com.example.campushub.studentassignment.dto.StudentAssignmentResponse;

public interface StudentAssignmentRepositoryCustom {
	List<StudentAssignmentResponse> getAllStudentAssignments(StudentAssigmentSearchCondition cond);
	Optional<StudentAssignFindOneDto> getStudentAssignment(Long StudentAssignmentId);

}

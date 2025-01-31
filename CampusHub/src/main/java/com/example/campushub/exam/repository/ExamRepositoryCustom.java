package com.example.campushub.exam.repository;

import com.example.campushub.exam.dto.ExamFindAllResponse;
import com.example.campushub.exam.dto.ExamScoreInputRequest;
import com.example.campushub.exam.dto.ExamSearchCondition;

import java.util.List;

public interface ExamRepositoryCustom {

    List<ExamFindAllResponse> findExamScoresByUserNums(List<String> userNums, ExamSearchCondition cond);

    void updateExamScore(ExamScoreInputRequest request);
}

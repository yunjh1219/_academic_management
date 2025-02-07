package com.example.campushub.exam.dto;


import com.querydsl.core.annotations.QueryProjection;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExamScoreInputRequest {
    private Long examId;
    @NotBlank(message = "중간 점수를 기입해주세요")
    private int midScore;
    @NotBlank(message = "기말 점수를 기입해주세요")
    private int finalScore;
    @NotBlank(message = "총 점수를 입력해주세요")
    private int totalScore;


    @Builder
    @QueryProjection
    public ExamScoreInputRequest(Long examId, int midScore, int finalScore, int totalScore) {
        this.examId = examId;
        this.midScore = midScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
    }
}

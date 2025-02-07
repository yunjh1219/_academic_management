package com.example.campushub.exam.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExamScoreUpdateResponse {

    private Long examId;
    private int midScore;
     private int finalScore;
     private int totalScore;

     @Builder
    @QueryProjection
    public ExamScoreUpdateResponse(Long examId, int midScore, int finalScore, int totalScore) {
         this.examId = examId;
         this.midScore = midScore;
         this.finalScore = finalScore;
         this.totalScore = totalScore;
     }
}

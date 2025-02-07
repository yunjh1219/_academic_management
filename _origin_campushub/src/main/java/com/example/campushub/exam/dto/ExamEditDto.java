package com.example.campushub.exam.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamEditDto {

    @NotBlank(message = "중간 점수")
    private int midExamScore;

    @NotBlank(message = "기말 점수")
    private int finalExamScore;

    private int totalScore; // 총 점수 (계산용, 클라이언트에서 보내지 않음)

    @Builder
    public ExamEditDto(int midExamScore, int finalExamScore) {
        this.midExamScore = midExamScore;
        this.finalExamScore = finalExamScore;
        this.totalScore = midExamScore + finalExamScore;
    }
}

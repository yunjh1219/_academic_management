package com.example.campushub.exam.dto;
import com.querydsl.core.annotations.QueryProjection; import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class ExamFindAllResponse {
    private String username;
    private String userNum;
    private String deptName;
    private Long userCourseId; // ID
    private int midScore;
    private int finalScore;
    private int totalScore;


    @Builder
    @QueryProjection
    public ExamFindAllResponse(String username, String userNum, String deptName,
                               Long userCourseId, int midScore, int finalScore, int totalScore) {
        this.username = username;
        this.userNum = userNum;
        this.deptName = deptName;
        this.userCourseId = userCourseId;
        this.midScore = midScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
    } }
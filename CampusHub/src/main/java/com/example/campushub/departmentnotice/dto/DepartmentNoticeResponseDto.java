package com.example.campushub.departmentnotice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class DepartmentNoticeResponseDto {

    private Long id;              // 공지사항 ID
    private String title;         // 공지 제목
    private String content;       // 공지 내용
    private String professorName; // 교수명
    private String courseName;    // 강의명
    private Date createdAt;       // 작성 날짜

    @Builder
    @QueryProjection
    public DepartmentNoticeResponseDto(Long id, String title, String content, String professorName, String courseName, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.professorName = professorName;
        this.courseName = courseName;
        this.createdAt = createdAt;
    }
}

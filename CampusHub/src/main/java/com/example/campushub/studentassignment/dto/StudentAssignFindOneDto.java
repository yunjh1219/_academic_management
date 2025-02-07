package com.example.campushub.studentassignment.dto;

import com.example.campushub.nweek.domain.Week;
import com.example.campushub.studentassignment.domain.SubmitStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentAssignFindOneDto {
	private String courseName;
	private String week;
	private String  userName;
	private String userNum;
	private String deptName;
	private String submitStatus;
	private String title;
	private String content;

	@Builder
	@QueryProjection
	public StudentAssignFindOneDto(String courseName, Week week, String userName, String userNum, String deptName,
		SubmitStatus submitStatus, String title, String content) {
		this.courseName = courseName;
		this.week = week.getName();
		this.userName = userName;
		this.userNum = userNum;
		this.deptName = deptName;
		this.submitStatus = submitStatus.getMessage();
		this.title = title;
		this.content = content;
	}
}

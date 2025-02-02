package com.example.campushub.attendance.dto;

import com.example.campushub.attendance.domain.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AttendanceSummaryDto {
	private String userName;
	private String userNum;
	private String week1;   // FIRST 주차 출석 상태
	private String week2;   // SECOND 주차 출석 상태
	private String week3;   // THIRD 주차 출석 상태
	private String week4;
	private String week5;
	private String week6;
	private String week7;
	private String week8;
	private String week9;
	private String week10;
	private String week11;
	private String week12;
	private String week13;
	private String week14;
	private String week15;
	private String week16;

	@JsonProperty("week1Korean")
	public String getWeek1Korean() {
		return AttendanceStatus.valueOf(week1).getKoreanName();
	}

	@JsonProperty("week2Korean")
	public String getWeek2Korean() {
		return AttendanceStatus.valueOf(week2).getKoreanName();
	}

	@JsonProperty("week3Korean")
	public String getWeek3Korean() {
		return AttendanceStatus.valueOf(week3).getKoreanName();
	}

	@JsonProperty("week4Korean")
	public String getWeek4Korean() {
		return AttendanceStatus.valueOf(week4).getKoreanName();
	}

	@JsonProperty("week5Korean")
	public String getWeek5Korean() {
		return AttendanceStatus.valueOf(week5).getKoreanName();
	}

	@JsonProperty("week6Korean")
	public String getWeek6Korean() {
		return AttendanceStatus.valueOf(week6).getKoreanName();
	}

	@JsonProperty("week7Korean")
	public String getWeek7Korean() {
		return AttendanceStatus.valueOf(week7).getKoreanName();
	}

	@JsonProperty("week8Korean")
	public String getWeek8Korean() {
		return AttendanceStatus.valueOf(week8).getKoreanName();
	}

	@JsonProperty("week9Korean")
	public String getWeek9Korean() {
		return AttendanceStatus.valueOf(week9).getKoreanName();
	}

	@JsonProperty("week10Korean")
	public String getWeek10Korean() {
		return AttendanceStatus.valueOf(week10).getKoreanName();
	}

	@JsonProperty("week11Korean")
	public String getWeek11Korean() {
		return AttendanceStatus.valueOf(week11).getKoreanName();
	}

	@JsonProperty("week12Korean")
	public String getWeek12Korean() {
		return AttendanceStatus.valueOf(week12).getKoreanName();
	}

	@JsonProperty("week13Korean")
	public String getWeek13Korean() {
		return AttendanceStatus.valueOf(week13).getKoreanName();
	}

	@JsonProperty("week14Korean")
	public String getWeek14Korean() {
		return AttendanceStatus.valueOf(week14).getKoreanName();
	}

	@JsonProperty("week15Korean")
	public String getWeek15Korean() {
		return AttendanceStatus.valueOf(week15).getKoreanName();
	}

	@JsonProperty("week16Korean")
	public String getWeek16Korean() {
		return AttendanceStatus.valueOf(week16).getKoreanName();
	}
}

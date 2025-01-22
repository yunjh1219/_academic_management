package com.example.campushub.assignment.domain;

import java.util.Date;

import com.example.campushub.nweek.domain.NWeek;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assignment {

	@Id
	@Column(name = "assignment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nweek_id")
	private NWeek nWeek;

	//과제 설명(변수명 제정의 필요)
	private String explan;
	//제출 기한(변수명 제정의 필요)
	private Date date;
	//첨부파일

	@Builder
	public Assignment(NWeek nWeek, String explan, Date date) {
		this.nWeek = nWeek;
		this.explan = explan;
		this.date = date;
	}
}

package com.example.campushub.assignment.domain;

import java.time.LocalDate;
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
	private String explain;
	private LocalDate createDate;
	private LocalDate limitDate;
	//첨부파일

	@Builder
	public Assignment(NWeek nWeek, String explain,LocalDate createDate, LocalDate limitDate) {
		this.nWeek = nWeek;
		this.explain = explain;
		this.createDate = createDate;
		this.limitDate = limitDate;
	}
}

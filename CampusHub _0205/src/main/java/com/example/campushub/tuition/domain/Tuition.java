package com.example.campushub.tuition.domain;

import static jakarta.persistence.FetchType.*;

import com.example.campushub.dept.domain.Dept;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tuition {

	@Id
	@Column(name = "tuition_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "dept_id")
	private Dept dept;

	private int tuitionFee;

	@Builder
	public Tuition(Dept dept, int tuitionFee) {
		this.dept = dept;
		this.tuitionFee = tuitionFee;
	}
}

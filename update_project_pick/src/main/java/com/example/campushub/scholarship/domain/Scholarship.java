package com.example.campushub.scholarship.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scholarship {
	@Id
	@Column(name = "scholarship_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String scholarshipName;
	@Enumerated(EnumType.STRING)
	private PaymentType type;
	private int amount;

	@Builder
	public Scholarship(String scholarshipName, int amount, PaymentType type) {
		this.scholarshipName = scholarshipName;
		this.amount = amount;
		this.type = type;
	}


}

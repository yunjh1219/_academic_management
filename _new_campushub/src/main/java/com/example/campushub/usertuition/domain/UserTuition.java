package com.example.campushub.usertuition.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDate;

import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.tuition.domain.Tuition;
import com.example.campushub.user.domain.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTuition {

	@Id
	@Column(name = "user_tuition_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "tuition_id")
	private Tuition tuition;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "school_year_id")
	private SchoolYear schoolYear;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	private LocalDate paymentDate;

	@Builder
	public UserTuition(User user, Tuition tuition, SchoolYear schoolYear,
					   PaymentStatus paymentStatus, LocalDate paymentDate) {
		this.user = user;
		this.tuition = tuition;
		this.schoolYear = schoolYear;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}

	public void updatePaymentStatusToSuccess() {
		this.paymentStatus = PaymentStatus.SUCCESS;
	}
	public void updatePaymentStatusToWait() {
		this.paymentStatus = PaymentStatus.WAITING;
	}
	public boolean isWaitPaymentStatus() {
		return this.paymentStatus == PaymentStatus.NONE;
	}

	public boolean isWaitingPaymentStatus() {
		return this.paymentStatus == PaymentStatus.WAITING;
	}


}

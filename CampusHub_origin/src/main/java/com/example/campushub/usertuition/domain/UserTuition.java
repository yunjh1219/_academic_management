package com.example.campushub.usertuition.domain;

import static jakarta.persistence.FetchType.*;

import java.util.Date;

import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.tuition.domain.Tuition;
import com.example.campushub.user.domain.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "tuition_id")
	private Tuition tuition;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "school_year_id")
	private SchoolYear schoolYear;

	private boolean paymentStatus;
	private Date paymentDate;
}

package com.example.campushub.userscholarship.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDate;
import java.util.Date;

import com.example.campushub.scholarship.domain.Scholarship;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.user.domain.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserScholarship {

	@Id
	@Column(name = "user_scholarship_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "scholarship_id")
	private Scholarship scholarship;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "school_year_id")
	private SchoolYear schoolYear;

	private LocalDate confDate;

	@Builder
	public UserScholarship(User user, Scholarship scholarship, SchoolYear schoolYear, LocalDate confDate) {
		this.user = user;
		this.scholarship = scholarship;
		this.schoolYear = schoolYear;
		this.confDate = confDate;
	}
}

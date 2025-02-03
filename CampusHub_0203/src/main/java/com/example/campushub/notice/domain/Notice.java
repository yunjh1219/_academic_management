package com.example.campushub.notice.domain;

import static jakarta.persistence.FetchType.*;

import java.util.Date;

import com.example.campushub.user.domain.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

	@Id
	@Column(name = "notice_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String title;
	private String content;
	private Date createdAt;

	@Builder
	public Notice(User user, String title, String content, Date createdAt) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}

package com.example.campushub.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
	//휴학, 재학, 복학대기, 휴학대기, 재직
    BREAK("BREAK"), ENROLLED("ENROLLED"), RETURN_PENDING("RETURN_PENDING"), BREAK_PENDING("BREAK_PENDING"), EMPLOYED("EMPLOYED");

	private final String message;

}

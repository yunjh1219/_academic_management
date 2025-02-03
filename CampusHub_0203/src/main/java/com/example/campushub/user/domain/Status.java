package com.example.campushub.user.domain;

import com.example.campushub.nweek.domain.Week;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
	//휴학, 재학, 복학대기, 휴학대기, 재직
    BREAK("BREAK"), ENROLLED("ENROLLED"), RETURN_PENDING("RETURN_PENDING"), BREAK_PENDING("BREAK_PENDING"), EMPLOYED("EMPLOYED");

	private final String message;

	//BREAK: 휴식
	//ENROLLED: 등록됨
	//RETURN_PENDING: 반납 대기 중
	//BREAK_PENDING: 휴식 대기 중
	//EMPLOYED: 고용됨

}

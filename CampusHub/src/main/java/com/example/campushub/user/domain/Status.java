package com.example.campushub.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
	//휴학, 재학, 복학대기, 휴학대기, 재직
    BREAK("휴학"), ENROLLED("재학"), RETURN_PENDING("복학대기"), BREAK_PENDING("휴학대기"), EMPLOYED("재직");

	private final String message;

	public static Status of(String koreanName){
		if(koreanName.equals("휴학")){
			return BREAK;
		} else if (koreanName.equals("재학")) {
			return ENROLLED;
		} else if (koreanName.equals("복학대기")) {
			return RETURN_PENDING;
		} else if (koreanName.equals("휴학대기")) {
			return BREAK_PENDING;
		}else return EMPLOYED;
	}

}

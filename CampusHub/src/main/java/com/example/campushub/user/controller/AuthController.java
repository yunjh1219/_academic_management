package com.example.campushub.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import com.example.campushub.global.common.SuccessResponse;
import com.example.campushub.global.security.AccessToken;
import com.example.campushub.global.security.Login;
import com.example.campushub.global.security.RefreshToken;
import com.example.campushub.global.security.Token;
import com.example.campushub.user.dto.JoinRequestDto;
import com.example.campushub.user.dto.LoginRequestDto;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final RestClient.Builder builder;



	//로그인
	@PostMapping("/api/login")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> signIn(@RequestBody @Valid LoginRequestDto loginDto, HttpServletResponse response) {
		Token token = authService.login(loginDto);

		setAccessToken(response, token.getAccessToken());
		setRefreshToken(response, token.getRefreshToken());

		return SuccessResponse.<Void>builder()
			.status(200)
			.message("로그인 성공")
			.build();
	}
	//학생 등록
	@PostMapping("/api/join/student")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> signUpStudent(@Login LoginUser loginUser, @RequestBody @Valid JoinRequestDto requestDto, HttpServletResponse response) {
		authService.joinStudent(loginUser, requestDto);

		return SuccessResponse.<Void>builder()
			.status(200)
			.message("학생 등록 성공")
			.build();
	}
	//교수 등록
	@PostMapping("/api/join/professor")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> signUpProfessor(@Login LoginUser loginUser, @RequestBody @Valid JoinRequestDto requestDto, HttpServletResponse response) {
		authService.joinProfessor(loginUser, requestDto);

		return SuccessResponse.<Void>builder()
			.status(200)
			.message("교수 등록 성공")
			.build();
	}
	//토큰 재발급
	@PostMapping("/api/reissue")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> reissueToken(RefreshToken refreshToken, HttpServletResponse response) {
		Token token = authService.reissue(refreshToken);

		setAccessToken(response, token.getAccessToken());
		setRefreshToken(response, token.getRefreshToken());

		return SuccessResponse.<Void>builder()
			.status(200)
			.message("토큰 재발급 성공")
			.build();
	}

	//로그아웃
	@PostMapping("/api/logout")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse<Void> logout(@Login LoginUser loginUser, HttpServletResponse response) {
		authService.logout(loginUser);

		removeCookie(response);

		return SuccessResponse.<Void>builder()
			.status(200)
			.message("로그아웃 성공")
			.build();
	}

	private void setAccessToken(HttpServletResponse response, AccessToken accessToken) {
		setHeader(response, accessToken.getHeader(), accessToken.getData());
	}

	private void setRefreshToken(HttpServletResponse response, RefreshToken refreshToken) {
		Cookie cookie = createCookie(refreshToken.getHeader(), refreshToken.getData());
		response.addCookie(cookie);
	}
	private Cookie createCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(RefreshToken.EXPIRATION_PERIOD);
		cookie.setHttpOnly(true);
		return cookie;
	}
	private void removeCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("Authorization-refresh", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);

		response.addCookie(cookie);
	}
	private void setHeader(HttpServletResponse response, String header, String data) {
		response.setHeader(header, data);
	}


}

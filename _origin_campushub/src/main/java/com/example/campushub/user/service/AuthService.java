package com.example.campushub.user.service;

import com.example.campushub.global.error.exception.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.campushub.dept.domain.Dept;
import com.example.campushub.dept.repository.DeptRepository;
import com.example.campushub.global.security.JwtProvider;
import com.example.campushub.global.security.RefreshToken;
import com.example.campushub.global.security.Token;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.JoinRequestDto;
import com.example.campushub.user.dto.LoginRequestDto;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.dto.PasswordChangeRequest;
import com.example.campushub.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final DeptRepository deptRepository;

	//로그인
	@Transactional
	public Token login(LoginRequestDto loginRequestDto) {
		String userNum = loginRequestDto.getUserNum();
		String password = loginRequestDto.getPassword();
		Type type = loginRequestDto.getType();

		User user = userRepository.findByUserNum(userNum)
			.orElseThrow(InvalidSigningInformation::new);

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidSigningInformation();
		}

		if (!user.isSameType(type)) {
			throw new InvalidSigningInformation();
		}

		Token token = jwtProvider.createToken(user.getUserNum(), user.getType(), user.getRole());

		user.updateRefreshToken(token.getRefreshToken().getData());

		return token;
	}

	//학생 등록
	@Transactional
	public void joinStudent(LoginUser loginUser, JoinRequestDto joinRequestDto) {
		userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.ADMIN)
			.orElseThrow(UserNotFoundException::new);

		if (userRepository.existsByUserNum(joinRequestDto.getUserNum())) {
			throw new DuplicateUserNumException();
		}

		Dept dept = deptRepository.findByDeptName(joinRequestDto.getDeptName())
				.orElseThrow(InvalidDeptException::new);

		joinRequestDto.passwordEncryption(passwordEncoder);

		userRepository.save(joinRequestDto.toStudentEntity(dept));
	}

	//교수 등록
	@Transactional
	public void joinProfessor(LoginUser loginUser, JoinRequestDto joinRequestDto) {
		userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.ADMIN)
			.orElseThrow(UserNotFoundException::new);

		if (userRepository.existsByUserNum(joinRequestDto.getUserNum())) {
			throw new DuplicateUserNumException();
		}

		Dept dept = deptRepository.findByDeptName(joinRequestDto.getDeptName())
			.orElseThrow(InvalidDeptException::new);

		joinRequestDto.passwordEncryption(passwordEncoder);

		userRepository.save(joinRequestDto.toProfessorEntity(dept));
	}

	//토큰 재발급
	@Transactional
	public Token reissue(RefreshToken refreshToken) {
		String refreshTokenValue = refreshToken.getData();

		if (!jwtProvider.isTokenValid(refreshTokenValue)) {
			throw new InvalidTokenException();
		}

		User user = userRepository.findByRefreshToken(refreshTokenValue)
			.orElseThrow(UserNotFoundException::new);
		Token token = jwtProvider.createToken(user.getUserNum(), user.getType(), user.getRole());

		user.updateRefreshToken(token.getRefreshToken().getData());

		return token;
	}

	//로그아웃
	@Transactional
	public void logout(LoginUser loginUser) {
		User user = userRepository.findByUserNum(loginUser.getUserNum())
			.orElseThrow(UserNotFoundException::new);

		user.invalidateRefreshToken();
	}

	//비밀번호 변경
	@Transactional
	public void changePassword(LoginUser loginUser, PasswordChangeRequest request) {
		User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
			.orElseThrow(UserNotFoundException::new);

		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new NotMatchPasswordException();
		}

		user.changePassword(passwordEncoder.encode(request.getNewPassword()));
	}
}

package com.example.campushub.user.repository;

import java.util.List;
import java.util.Optional;

import com.example.campushub.user.domain.Role;
import com.example.campushub.user.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.campushub.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{
	Optional<User> findByRefreshToken(String refreshToken);

	Optional<User> findByUserNum(String userNum);

	boolean existsByUserNum(String userNum);

	Optional<User> findByUserNumAndType(String userNum, Type type);

	@Query("select u from User u where u.userNum in :userNums")
	List<User> findAllByUserNums(List<String> userNums);
}

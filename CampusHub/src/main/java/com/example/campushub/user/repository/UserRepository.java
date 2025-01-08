package com.example.campushub.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.campushub.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}

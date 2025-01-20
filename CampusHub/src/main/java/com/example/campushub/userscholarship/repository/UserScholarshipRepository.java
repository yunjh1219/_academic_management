package com.example.campushub.userscholarship.repository;

import com.example.campushub.user.domain.User;
import com.example.campushub.userscholarship.domain.UserScholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserScholarshipRepository extends JpaRepository<UserScholarship, Long> {



}

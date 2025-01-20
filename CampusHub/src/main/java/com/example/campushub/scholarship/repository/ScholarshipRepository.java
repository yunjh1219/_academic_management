package com.example.campushub.scholarship.repository;

import com.example.campushub.scholarship.domain.Scholarship;
import com.example.campushub.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long>, ScholarshipRepositoryCustom {


}

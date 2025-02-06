package com.example.campushub.usertuition.repository;

import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.user.domain.User;
import com.example.campushub.usertuition.domain.UserTuition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserTuitionRepository extends JpaRepository<UserTuition, Long> {


    @Query("select ut from UserTuition ut join fetch ut.user u where u.id in :userIds")
    List<UserTuition> findAllByUserIds(List<Long> userIds);

    UserTuition findByUser(User user);
    UserTuition findByUserAndTuitionId(User user, Long tuitionId);


    @Query("SELECT ut FROM UserTuition ut WHERE ut.user = :user AND ut.tuition.id = :tuitionId AND ut.schoolYear = :schoolYear ")
    Optional<UserTuition> findByUserAndTuitionIdAndSchoolYear(User user, Long tuitionId, SchoolYear schoolYear);


}

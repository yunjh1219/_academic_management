package com.example.campushub.tuition.repository;

import com.example.campushub.tuition.dto.TuitionFindAllResponse;
import com.example.campushub.tuition.dto.TuitionSearchCondition;
import com.example.campushub.tuition.dto.TuitionStudentResponse;

import java.util.List;
import java.util.Optional;

public interface TuitionRepositoryCustom {

    List<TuitionFindAllResponse> findAllByCondition(TuitionSearchCondition cond);
    Optional<TuitionStudentResponse> findStudentTuitionDetail(String userNum);
    void updatePaymentStatusToWaiting(String userNum);

}

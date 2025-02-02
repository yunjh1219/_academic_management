package com.example.campushub.tuition.service;

import com.example.campushub.global.error.exception.TuitionNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.tuition.dto.TuitionFindAllResponse;
import com.example.campushub.tuition.dto.TuitionSearchCondition;
import com.example.campushub.tuition.dto.TuitionStudentResponse;
import com.example.campushub.tuition.repository.TuitionRepository;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.usertuition.domain.UserTuition;
import com.example.campushub.usertuition.repository.UserTuitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TuitionService {

    private final UserRepository userRepository;
    private final TuitionRepository tuitionRepository;
    private final UserTuitionRepository userTuitionRepository;

    //등록금 생성

    //등록금 조회
    public List<TuitionFindAllResponse> findTuitions(LoginUser loginUser, TuitionSearchCondition cond) {
        userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);
        return tuitionRepository.findAllByCondition(cond);
    }
    //등록 여부 변경
    @Transactional
    public void updatePaymentStatus(LoginUser loginUser, List<String> userNums) {
        userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                 .orElseThrow(UserNotFoundException::new);


        //2. userNums에 해당하는 사용자 전체 조회
        List<User> users = userRepository.findAllByUserNums(userNums);

        //3. user 객체에서 id만 파싱
        List<Long> userIds = users.stream().map(User::getId).toList();

        //4. ids에 해당하는 userTuition을 업데이트
        List<UserTuition> userTuitions = userTuitionRepository.findAllByUserIds(userIds);

        for
        (UserTuition userTuition : userTuitions) {
            if (!userTuition.isWaitingPaymentStatus()) {
                throw new IllegalArgumentException("ERROR");
            }
            userTuition.updatePaymentStatusToSuccess();
        }
    }

    //학생 (본인) 등록금 조회
    @Transactional
    public TuitionStudentResponse getStudentTuitionDetail(LoginUser loginUser) {
        System.out.println("getStudentTuitionDetail called with userNum: " + loginUser.getUserNum());

        //로그인한 사용자 확인
        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.STUDENT)
                .orElseThrow(UserNotFoundException::new);

        TuitionStudentResponse response = tuitionRepository.findStudentTuitionDetail(user.getUserNum())
                .orElseThrow(TuitionNotFoundException::new);

        return response;
    }

    //학생 등록금 납부(납부 여부 변경 신청)
    @Transactional
    public void applyTuitionPayment(LoginUser loginUser, Long tuitionId) {
        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.STUDENT)
                .orElseThrow(UserNotFoundException::new);

        UserTuition ut = userTuitionRepository.findByUserAndTuitionId(user, tuitionId);

        if (!ut.isWaitPaymentStatus())
            throw new IllegalArgumentException("ERROR");
        ut.updatePaymentStatusToWait();
    }

}

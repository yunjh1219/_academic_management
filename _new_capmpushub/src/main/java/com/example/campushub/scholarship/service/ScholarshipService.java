package com.example.campushub.scholarship.service;


import com.example.campushub.global.error.exception.SchoolYearNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.scholarship.domain.Scholarship;
import com.example.campushub.scholarship.dto.GetMyScholarshipDto;
import com.example.campushub.scholarship.dto.ScholarshipCreateDto;
import com.example.campushub.scholarship.dto.ScholarshipResponseDto;
import com.example.campushub.scholarship.dto.ScholarshipSearchCondition;
import com.example.campushub.scholarship.repository.ScholarshipRepository;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.domain.Semester;
import com.example.campushub.schoolyear.repository.SchoolYearRepository;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.dto.UserFindOneSimpleDto;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.userscholarship.domain.UserScholarship;
import com.example.campushub.userscholarship.repository.UserScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScholarshipService {

    private final UserRepository userRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final UserScholarshipRepository userScholarshipRepository;
    private final SchoolYearRepository schoolYearRepository;

    public List<ScholarshipResponseDto> findScholarships(ScholarshipSearchCondition cond, LoginUser loginUser) {
        // 1. 요청한 사용자가 ADMIN인지 확인
         userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                 .orElseThrow(UserNotFoundException::new);

        // 2. 조회
        return scholarshipRepository.findAllByCondition(cond);
    }

// 장학금 등록 서비스
    @Transactional
    public void createScholarship(ScholarshipCreateDto createDto , LoginUser loginUser) {

        //관리자인지 검증
       userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        User student = userRepository.findByUserNum(createDto.getUserNum())
                .orElseThrow(UserNotFoundException::new);

        SchoolYear schoolYear = schoolYearRepository.findBySemesterAndYear(
            Semester.of(createDto.getSemester()), Year.parse(createDto.getYear()))
            .orElseThrow(SchoolYearNotFoundException::new);

        Scholarship scholarship = createDto.toEntity();

        scholarshipRepository.save(scholarship);

        UserScholarship userScholarship = UserScholarship.builder()
                .user(student)
                .scholarship(scholarship)
                .schoolYear(schoolYear)
                .confDate(LocalDate.now())
                        .build();

        userScholarshipRepository.save(userScholarship);

    }
    // 학번 입력 시 , 이름과 학과 자동 조회 서비스
    public UserFindOneSimpleDto getUserSimpleInfo(String userNum){
        User user = userRepository.findByUserNum(userNum)
                .orElseThrow(UserNotFoundException::new);

        return new UserFindOneSimpleDto(user.getUserName(),user.getDept().getDeptName(),user.getUserNum());
    }



//        //학년도 리포지토리에서 커스텀으로 where 문에 년도, 학기를 넣는 쿼리를 만들어서 shcoolyear 타입 값을 담음
//        SchoolYear year = new SchoolYear(LocalDate.now(), Semester.first_semester,true);
//
//        Optional<User> findUser = userRepository.findByUserNum(createDto.getUserNum());
//
//        Scholarship scholarship = new Scholarship("asdf", 1, PaymentType.PRE_PAYMENT);
//
//        // 확정날짜 bulid
//        UserScholarship userScholarship = UserScholarship.builder()
//                .user(user)
//                .schoolYear(year)
//                .scholarship(scholarship)
//                .confDate(LocalDate.now())
//                        .build();
//




////    사용자의 장학금 삭제
//    @Transactional
//    public void deleteScholarship(String userNum , LoginUser loginUser){
//        // 요청한 사용자가 ADMIN인지?
//        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(),"ADMIN")
//                .orElseThrow(;
//        // 삭제 대상 사용자
//        User deleteuser = userRepository.findByUserNum(userNum)
//                        .orElseThrow(() -> new IllegalArgumentException("삭제할려는 사용자를 찾을 수 없습니다"));
//
//            userScholarshipRepository.deleteByUserNum(deleteuser.getUserNum());
//            scholarshipRepository.deleteByUserNum(deleteuse);
//
//
//        System.out.println("사용자" + userNum +"의 장학금을 삭제했습니다");
//
//    }

    //사용자 본인 장학금 조회
    public List<GetMyScholarshipDto> findMyScholarships(LoginUser loginUser) {
        User user = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        return scholarshipRepository.findAllMyScholarship(user);

    }
}

package com.example.campushub.attendance.service;


import com.example.campushub.attendance.domain.Attendance;
import com.example.campushub.attendance.domain.AttendanceStatus;
import com.example.campushub.attendance.dto.*;
import com.example.campushub.attendance.repository.AttendanceRepository;
import com.example.campushub.course.domain.Course;
import com.example.campushub.course.repository.CourseRepository;
import com.example.campushub.global.error.exception.AttendanceConditionNotSetException;
import com.example.campushub.global.error.exception.CourseNotFoundException;
import com.example.campushub.global.error.exception.NWeekNotFoundException;
import com.example.campushub.global.error.exception.UserCourseNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import com.example.campushub.nweek.repository.NweekRepository;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.usercourse.domain.UserCourse;
import com.example.campushub.usercourse.repository.UserCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;
    private final NweekRepository nweekRepository;
    private final CourseRepository courseRepository;


    //출석 조회 (강의명, 주차 설정)
    public List<AttendanceResponseDto> findAttendance(AttendanceSearchCondition cond, LoginUser loginUser) {

        userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
            .orElseThrow(UserNotFoundException::new);

        if (cond.getCourseName() == null || cond.getWeek() == null) {
            throw new AttendanceConditionNotSetException();
        }

        return attendanceRepository.findAllByCondition(cond);
    }


    //드롭다운을 통한 출결 설정.
    @Transactional
    public void createAttendance(LoginUser loginUser, List<AttendanceRequestDto> requestDto,AttendanceSearchCondition cond) {
        //교수인지 확인
         userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        for(AttendanceRequestDto request : requestDto){

            User student = userRepository.findByUserNum(request.getUserNum())
                    .orElseThrow(UserNotFoundException::new);

            Course course = courseRepository.findByCourseName(cond.getCourseName())
                .orElseThrow(CourseNotFoundException::new);

            UserCourse userCourse = userCourseRepository.findByUserAndCourse(student, course)
                .orElseThrow(UserCourseNotFoundException::new);

            NWeek nWeek = nweekRepository.findByCourseAndWeek(course, Week.valueOf(cond.getWeek()))
                .orElseThrow(NWeekNotFoundException::new);

            Attendance attendance = Attendance.builder()
                    .nWeek(nWeek)
                    .userCourse(userCourse)
                    .status(AttendanceStatus.valueOf(request.getStatus()))
                    .build();

            attendanceRepository.save(attendance);

        }

    }

    //출석통계 조회
    public List<AttendanceSummaryDto> findAllAttendance(LoginUser loginUser, AttendanceSearchCourseCondition cond) {
        User professor = userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
            .orElseThrow(UserNotFoundException::new);

        //16주차에 츨석status 값이 null이면 오류
        List<AttendanceSummaryDto> courseByCondition = attendanceRepository.findAttendanceByCondition(cond);

        if (courseByCondition.contains(null)) {
            throw new IllegalArgumentException("조회된 리스트에 null 값이 있습니다");
        }
        return courseByCondition;

    }
    //        // 총결석 일수 조회
    //      @Transactional
    //    public int cntAbsence(List<AllAttendanceResponseDto> attenList){
    //        int cnt = 0;
    //
    //        for(AllAttendanceResponseDto dto : attenList){
    //            if (dto.getStatus() == AttendanceStatus.ABSENCE){
    //                cnt++;
    //            }
    //        }
    //        return cnt;
    //      }
    //
    //      //출석 점수 로직
    //      @Transactional
    //    public int attendanceScore(List<AllAttendanceResponseDto> attenList){
    //        int totalScore = 100;
    //
    //        //주차 별 출결상태를 통한 출석점수 감점
    //        for(AllAttendanceResponseDto dto : attenList){
    //            if (dto.getStatus() == AttendanceStatus.ABSENCE){
    //                totalScore -= 3;
    //            } else if (dto.getStatus() == AttendanceStatus.PERCEPTION){
    //                totalScore -= 1;
    //            }
    //        }
    //            //결석횟수가 3회 이상 시, 0점 처리
    //          int abSenceCount = cntAbsence(attenList);
    //          if (abSenceCount >= 3){
    //              totalScore = 0;
    //          }
    //            return totalScore;
    //      }

    //학생 본인 출석 조회
    // public List<AttendanceUserDto> userAttendacnce(LoginUser loginUser,AttendanceSearchCondition atten){
    //
    //     User student = userRepository.findByUserNumAndType(loginUser.getUserNum(),loginUser.getType())
    //             .orElseThrow(UserNotFoundException::new);
    //
    //     return attendanceRepository.findUserAttendance(atten,student.getUserNum());
    //
    //   }

}
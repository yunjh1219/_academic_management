package com.example.campushub.attendance.service;


import com.example.campushub.attendance.domain.Attendance;
import com.example.campushub.attendance.domain.AttendanceStatus;
import com.example.campushub.attendance.dto.*;
import com.example.campushub.attendance.repository.AttendanceRepository;
import com.example.campushub.course.domain.Course;
import com.example.campushub.course.repository.CourseRepository;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.nweek.domain.NWeek;
import com.example.campushub.nweek.domain.Week;
import com.example.campushub.nweek.repository.NweekRepository;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import com.example.campushub.usercourse.domain.UserCourse;
import com.example.campushub.usercourse.repository.UserCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public List<AttendanceResponseDto> findAttendance(AttendanceSearchCondition atten, LoginUser loginUser) {
            User professor = userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.PROFESSOR)
                    .orElseThrow(UserNotFoundException::new);

            return attendanceRepository.findAllByCondition(atten);
    }


    //드롭다운을 통한 출결 변경
    @Transactional
    public void createAttendance(LoginUser loginUser, List<AttendanceResponseDto> atten,AttendanceSearchCondition cond) {
        //교수인지 확인
        User professor = userRepository.findByUserNumAndType(loginUser.getUserNum(), Type.PROFESSOR)
                .orElseThrow(UserNotFoundException::new);

        for(AttendanceResponseDto dto : atten){

            User student = userRepository.findByUserNum(dto.getUserNum())
                    .orElseThrow(UserNotFoundException::new);

            Course course = courseRepository.findCourseByCourseName(cond.getCourseName());

            UserCourse userCourse = userCourseRepository.findByCourseAndUser(course, student);

            NWeek nWeek = nweekRepository.findByWeek(cond.getWeek());

            Attendance attendance = Attendance.builder()
                    .nWeek(nWeek)
                    .userCourse(userCourse)
                    .status(dto.getStatus())
                    .build();

            attendanceRepository.save(attendance);

        }




      }
        //출석통계 조회
    public List<AllAttendanceResponseDto> findAllAttendance(LoginUser loginUser, AttendanceSearchCourseCondition cond) {
        User professor =  userRepository.findByUserNumAndType(loginUser.getUserNum(),Type.PROFESSOR)
                .orElseThrow(UserNotFoundException::new);

            return attendanceRepository.findCourseByCondition(cond);

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
    public List<AttendanceUserDto> userAttendacnce(LoginUser loginUser,AttendanceSearchCondition atten){

        User student = userRepository.findByUserNumAndType(loginUser.getUserNum(),Type.STUDENT)
                .orElseThrow(UserNotFoundException::new);

        return attendanceRepository.findUserAttendance(atten,student.getUserNum());

      }
}

package com.example.campushub.exam.service;

import com.example.campushub.course.domain.Course;
import com.example.campushub.course.repository.CourseRepository;
import com.example.campushub.exam.domain.Exam;
import com.example.campushub.exam.dto.ExamEditDto;
import com.example.campushub.exam.dto.ExamFindAllResponse;
import com.example.campushub.exam.dto.ExamScoreInputRequest;
import com.example.campushub.exam.dto.ExamScoreUpdateResponse;
import com.example.campushub.exam.dto.ExamSearchCondition;
import com.example.campushub.exam.repository.ExamRepository;
import com.example.campushub.global.error.exception.CourseNotFoundException;
import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.user.domain.Type;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.campushub.global.error.exception.ExamNotFoundException;
import com.example.campushub.usercourse.domain.UserCourse;
import com.example.campushub.usercourse.repository.UserCourseRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamService {

    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final UserCourseRepository userCourseRepository;
    private final CourseRepository courseRepository;

    //교수 성적 조회
    // .todo 컨디션으로 조회
    public List<ExamFindAllResponse> getExamScores(LoginUser loginUser, ExamSearchCondition cond) {
         userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        Course course = courseRepository.findByCourseName(cond.getCourseName())
            .orElseThrow(CourseNotFoundException::new);

        List<UserCourse> userCourses = userCourseRepository.findAllByCourse(course);

        List<String> userNums = userCourses.stream()
            .map(userCourse -> userCourse.getUser().getUserNum())
            .toList();

        return examRepository.findExamScoresByUserNums(userNums, cond);
    }


    //성적 기입
    @Transactional
    public void updateExamScore(LoginUser loginUser, ExamScoreInputRequest request, Long userCourseId) {
        userRepository.findByUserNumAndType(loginUser.getUserNum(), loginUser.getType())
            .orElseThrow(UserNotFoundException::new);

        UserCourse userCourse = userCourseRepository.findById(userCourseId)
            .orElseThrow(UserNotFoundException::new);

        Exam exam = examRepository.findByUserCourse(userCourse);

        if(exam == null) {

            Exam exam1 = Exam.builder()
                .userCourse(userCourse)
                .midScore(request.getMidScore())
                .finalScore(request.getFinalScore())
                .totalScore(request.getTotalScore())
                .build();

            examRepository.save(exam1);
        }

        exam.edit(request.getMidScore(), request.getFinalScore(), request.getTotalScore());

        // 총점 계산 후 응답 DTO 생성
        // return ExamScoreUpdateResponse.builder()
        //         .examId(request.getExamId())
        //         .midScore(request.getMidScore())
        //         .finalScore(request.getFinalScore())
        //         .totalScore(request.getMidScore() + request.getFinalScore()) // 총점 계산
        //         .build();
    }


    // 점수 수정
    // @Transactional
    // public void editExam(Long examId, ExamEditDto editDto) {
    //     Exam exam = examRepository.findById(examId)
    //             .orElseThrow(ExamNotFoundException::new);
    //
    //     // totalScore 계산
    //     int totalScore = editDto.getMidExamScore() + editDto.getFinalExamScore();
    //
    //     // Exam 도메인의 edit 메서드 호출
    //     exam.edit(editDto.getMidExamScore(), editDto.getFinalExamScore(), totalScore);
    // }


}

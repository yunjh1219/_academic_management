package com.example.campushub.semsterschedule.service;


import com.example.campushub.global.error.exception.UserNotFoundException;
import com.example.campushub.schoolyear.domain.SchoolYear;
import com.example.campushub.schoolyear.repository.SchoolYearRepository;
import com.example.campushub.semsterschedule.domain.SemesterSchedule;
import com.example.campushub.semsterschedule.dto.SemesterScheduleRequest;
import com.example.campushub.semsterschedule.dto.SemesterScheduleResponse;
import com.example.campushub.semsterschedule.repository.SemesterScheduleRepository;
import com.example.campushub.user.domain.User;
import com.example.campushub.user.dto.LoginUser;
import com.example.campushub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SemesterScheduleService {

    private final SemesterScheduleRepository semesterScheduleRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final UserRepository userRepository;

    //학사일정 등록
    @Transactional
    public void createSemesterSchedule(SemesterScheduleRequest request,LoginUser loginUser) {

        userRepository.findByUserNumAndType(loginUser.getUserNum(),loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        //일정 저장
        SemesterSchedule semesterSchedule = SemesterSchedule.builder()
                .schedule(request.getSchedule())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .dateCheck(request.isDateCheck())
                .eventName(request.getEventName())
                .build();

        semesterScheduleRepository.save(semesterSchedule);
    }
    //특정 월의 학사일정 조회
    public List<SemesterScheduleResponse> getSchedulesByMonth(int year, int month,LoginUser loginUser) {
        userRepository.findByUserNumAndType(loginUser.getUserNum(),loginUser.getType())
                .orElseThrow(UserNotFoundException::new);

        return semesterScheduleRepository.findSchedulesByMonth(year, month);
    }

    //특정 날짜의 학사일정 조회
    public List<SemesterScheduleResponse> getSchedulesByDate(LocalDateTime date,LoginUser loginUser) {
        userRepository.findByUserNumAndType(loginUser.getUserNum(),loginUser.getType())
                .orElseThrow(UserNotFoundException::new);
        return semesterScheduleRepository.findSchedulesByDate(date);
    }

}

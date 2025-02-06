package com.example.campushub.semsterschedule.repository;


import com.example.campushub.semsterschedule.domain.QSemesterSchedule;
import com.example.campushub.semsterschedule.dto.QSemesterScheduleResponse;
import com.example.campushub.semsterschedule.dto.SemesterScheduleResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.campushub.semsterschedule.domain.QSemesterSchedule.semesterSchedule;

@Repository
@RequiredArgsConstructor
public class SemesterScheduleRepositoryCustomImpl implements SemesterScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<SemesterScheduleResponse> findSchedulesByMonth(int year, int month) {

        // 월의 첫 날과 마지막 날 계산
        LocalDateTime startOfMonth = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.of(year, month, startOfMonth.toLocalDate().lengthOfMonth())
                .atTime(23, 59, 59);
        return queryFactory.select(new QSemesterScheduleResponse(
                        semesterSchedule.id,
                        semesterSchedule.schedule,
                        semesterSchedule.startDate,
                        semesterSchedule.endDate,
                        semesterSchedule.dateCheck,
                        semesterSchedule.eventName
                ))
                .from(semesterSchedule).
                where(
                        // startDate 또는 endDate가 해당 월에 포함되면 가져오기
                        (semesterSchedule.startDate.between(startOfMonth, endOfMonth)
                        .or(semesterSchedule.endDate.between(startOfMonth, endOfMonth))
                        .or(
                            // startDate가 시작일 전에, endDate가 해당 월 안에 있는 경우
                             semesterSchedule.startDate.before(endOfMonth)
                             .and(semesterSchedule.endDate.after(startOfMonth))
                        ))
                )
                .fetch();
    }

        @Override
        public List<SemesterScheduleResponse> findSchedulesByDate (LocalDateTime date){
            QSemesterSchedule semesterSchedule = QSemesterSchedule.semesterSchedule;
            return queryFactory
                    .select(new QSemesterScheduleResponse(
                            semesterSchedule.id,
                            semesterSchedule.schedule,
                            semesterSchedule.startDate,
                            semesterSchedule.endDate,
                            semesterSchedule.dateCheck,
                            semesterSchedule.eventName
                    ))
                    .from(semesterSchedule).where(semesterSchedule.startDate.loe(date)
                            .and(semesterSchedule.endDate.goe(date)))
                    .fetch();
        }
    }
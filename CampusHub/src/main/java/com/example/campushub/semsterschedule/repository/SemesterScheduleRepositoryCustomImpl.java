package com.example.campushub.semsterschedule.repository;


import com.example.campushub.semsterschedule.domain.QSemesterSchedule;
import com.example.campushub.semsterschedule.dto.QSemesterScheduleResponse;
import com.example.campushub.semsterschedule.dto.SemesterScheduleResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.campushub.semsterschedule.domain.QSemesterSchedule.semesterSchedule;

@Repository
@RequiredArgsConstructor
public class SemesterScheduleRepositoryCustomImpl implements SemesterScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<SemesterScheduleResponse> findSchedulesByMonth(int year, int month) {
        QSemesterSchedule semesterSchedule = QSemesterSchedule.semesterSchedule;
        return queryFactory.select(new QSemesterScheduleResponse(
                        semesterSchedule.id, semesterSchedule.schoolYear.id.as("schoolYearId"), // ID
                        semesterSchedule.schedule,
                        semesterSchedule.startDate,
                        semesterSchedule.endDate,
                        semesterSchedule.dateCheck,
                        semesterSchedule.eventName
                ))
                .from(semesterSchedule).
                where(semesterSchedule.startDate.year().eq(year)
                        .and(semesterSchedule.startDate.month().eq(month)))
                .fetch();
    }

        @Override
        public List<SemesterScheduleResponse> findSchedulesByDate (LocalDateTime date){
            QSemesterSchedule semesterSchedule = QSemesterSchedule.semesterSchedule;
            return queryFactory
                    .select(new QSemesterScheduleResponse(
                            semesterSchedule.id, semesterSchedule.schoolYear.id.as("schoolYearId"), // ID
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
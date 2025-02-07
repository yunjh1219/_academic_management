// DTO for semester schedule registration
package com.example.campushub.semsterschedule.dto;
import com.example.campushub.semsterschedule.domain.Schedule; import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class SemesterScheduleRequest {
    // ID (SchoolYear ID)

    private Schedule schedule;
    //
    private LocalDateTime startDate;
    //
    private LocalDateTime endDate;
    //
    private boolean dateCheck;
    //
    private String eventName;
    @Builder
    @QueryProjection
    public SemesterScheduleRequest( Schedule schedule, LocalDateTime startDate, LocalDateTime endDate, boolean dateCheck, String eventName) {
         this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateCheck = dateCheck;
        this.eventName = eventName; }
}
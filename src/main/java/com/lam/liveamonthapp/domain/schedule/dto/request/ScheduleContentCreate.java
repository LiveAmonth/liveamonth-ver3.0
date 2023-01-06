package com.lam.liveamonthapp.domain.schedule.dto.request;

import lombok.*;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;
import com.lam.liveamonthapp.domain.schedule.entity.ScheduleContent;
import com.lam.liveamonthapp.global.dto.request.TimePeriodRequest;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleContentCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private TimePeriodRequest timePeriod;

    @Min(0)
    private int cost;

    @AssertTrue
    public boolean isValidTimePeriod() {
        return getTimePeriod().getStartDateTime().isBefore(getTimePeriod().getEndDateTime());
    }

    @Builder
    public ScheduleContentCreate(String title, String content, TimePeriodRequest timePeriod, int cost) {
        this.title = title;
        this.content = content;
        this.timePeriod = timePeriod;
        this.cost = cost;
    }

    public ScheduleContent toEntity(Schedule schedule) {
        return ScheduleContent.builder()
                .title(this.title)
                .content(this.content)
                .timePeriod(this.timePeriod.toEntity())
                .cost(this.cost)
                .schedule(schedule)
                .build();

    }
}

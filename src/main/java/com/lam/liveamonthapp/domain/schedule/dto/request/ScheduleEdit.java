package com.lam.liveamonthapp.domain.schedule.dto.request;

import lombok.*;
import com.lam.liveamonthapp.global.dto.request.PeriodRequest;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleEdit {

    @NotBlank
    private String title;

    @NotNull
    private String city;

    @NotNull
    private PeriodRequest period;

    @NotNull
    private boolean publicFlag;

    @AssertTrue
    public boolean isValidPeriod() {
        return getPeriod().getStartDate().isBefore(getPeriod().getEndDate());
    }

    @Builder
    public ScheduleEdit(String title, String city, PeriodRequest period, boolean publicFlag) {
        this.title = title;
        this.city = city;
        this.period = period;
        this.publicFlag = publicFlag;
    }
}

package com.lam.liveamonthapp.domain.schedule.entity;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.global.entity.TimePeriod;

@Getter
public class ScheduleContentEditor {

    private final String title;

    private final String content;

    private final TimePeriod timePeriod;

    private final long cost;

    @Builder
    public ScheduleContentEditor(String title, String content, TimePeriod timePeriod, long cost) {
        this.title = title;
        this.content = content;
        this.timePeriod = timePeriod;
        this.cost = cost;
    }
}

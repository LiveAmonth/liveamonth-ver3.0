package com.lam.liveamonthapp.domain.schedule.service;

import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleContentCreate;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleContentEdit;
import com.lam.liveamonthapp.domain.schedule.dto.response.ScheduleContentResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;

import java.util.List;

public interface ScheduleContentService {

    PostIdResponse addScheduleContent(Long scheduleId, ScheduleContentCreate request);

    void editScheduleContent(Long id, ScheduleContentEdit request);

    void deleteScheduleContent(Long id);

    List<ScheduleContentResponse> getScheduleContents(Long scheduleId);


}

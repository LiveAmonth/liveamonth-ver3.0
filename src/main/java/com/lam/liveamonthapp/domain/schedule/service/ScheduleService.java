package com.lam.liveamonthapp.domain.schedule.service;

import com.lam.liveamonthapp.domain.schedule.dto.condition.ScheduleSearchCond;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleCreate;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleEdit;
import com.lam.liveamonthapp.domain.schedule.dto.response.EditableScheduleResponse;
import com.lam.liveamonthapp.domain.schedule.dto.response.MyScheduleResponse;
import com.lam.liveamonthapp.domain.schedule.dto.response.ScheduleCardResponse;
import com.lam.liveamonthapp.global.dto.response.CountResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import java.util.List;

public interface ScheduleService {

    PostIdResponse addSchedule(String loginId, ScheduleCreate request);

    void editSchedule(Long scheduleId, ScheduleEdit request);

    void deleteSchedule(Long scheduleId);

    CustomPage<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO);

    List<MyScheduleResponse> getMySchedules(String loginId, Integer size, Long lastId);

    List<ScheduleCardResponse> getBestSchedules();

    List<ScheduleCardResponse> getFollowedSchedules(String loginId, Integer size, Long lastId);

    List<EditableScheduleResponse> getEditableSchedules(String loginId);

    CountResponse getNumberOfFollowedPosts(String loginId);

    void viewCountUp(Long scheduleId);

}

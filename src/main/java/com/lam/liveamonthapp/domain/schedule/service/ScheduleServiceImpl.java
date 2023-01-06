package com.lam.liveamonthapp.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.schedule.constants.ScheduleSortType;
import com.lam.liveamonthapp.domain.schedule.dto.condition.ScheduleSearchCond;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleCreate;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleEdit;
import com.lam.liveamonthapp.domain.schedule.dto.response.EditableScheduleResponse;
import com.lam.liveamonthapp.domain.schedule.dto.response.MyScheduleResponse;
import com.lam.liveamonthapp.domain.schedule.dto.response.ScheduleCardResponse;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;
import com.lam.liveamonthapp.domain.schedule.entity.ScheduleEditor;
import com.lam.liveamonthapp.domain.schedule.repository.core.ScheduleRepository;
import com.lam.liveamonthapp.domain.schedule.repository.query.ScheduleQueryRepository;
import com.lam.liveamonthapp.exception.notfound.ScheduleNotFound;
import com.lam.liveamonthapp.global.dto.response.CountResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.DomainSpec;
import com.lam.liveamonthapp.paging.PageableDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {
    private final SecurityContextFinder finder;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

    @Override
    @Transactional
    public PostIdResponse addSchedule(String loginId, ScheduleCreate request) {
        finder.checkLegalLoginId(loginId);
        return PostIdResponse.of(
                scheduleRepository.save(
                        request.toEntity(
                                finder.getLoggedInMember())
                ).getId());
    }

    @Override
    @Transactional
    public void editSchedule(Long scheduleId, ScheduleEdit request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFound::new);

        finder.checkLegalWriterOfPost(schedule);

        ScheduleEditor editor = schedule.toEditor()
                .title(request.getTitle())
                .period(request.getPeriod().toEntity())
                .publicFlag(request.isPublicFlag())
                .city(CityName.valueOf(request.getCity()))
                .build();

        schedule.edit(editor);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFound::new);
        finder.checkLegalWriterOfPost(schedule);

        scheduleRepository.delete(schedule);
    }

    @Override
    public CustomPage<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        Page<ScheduleCardResponse> page = scheduleQueryRepository.search(cond, pageable);

        return CustomPage.<ScheduleCardResponse>builder()
                .page(page)
                .build();
    }

    @Override
    public List<MyScheduleResponse> getMySchedules(String loginId, Integer size, Long lastId) {
        finder.checkLegalLoginId(loginId);
        return scheduleQueryRepository.getMySchedules(loginId, size, lastId);
    }

    @Override
    public List<ScheduleCardResponse> getBestSchedules() {
        return scheduleQueryRepository.getBestSchedules();

    }

    @Override
    public List<ScheduleCardResponse> getFollowedSchedules(String loginId, Integer size, Long lastId) {
        return scheduleQueryRepository.getFollowedSchedules(loginId, size, lastId);
    }

    @Override
    public List<EditableScheduleResponse> getEditableSchedules(String loginId) {
        finder.checkLegalLoginId(loginId);
        return scheduleQueryRepository.getEditableSchedules(loginId);
    }

    @Override
    public CountResponse getNumberOfFollowedPosts(String loginId) {
        return CountResponse.of(scheduleQueryRepository.getNumberOfFollowedPosts(loginId));
    }

    @Override
    @Transactional
    public void viewCountUp(Long scheduleId) {
        scheduleRepository.viewCountUp(scheduleId);
    }

}

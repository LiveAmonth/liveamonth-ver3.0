package com.lam.liveamonthapp.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleContentCreate;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleContentEdit;
import com.lam.liveamonthapp.domain.schedule.dto.response.ScheduleContentResponse;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;
import com.lam.liveamonthapp.domain.schedule.entity.ScheduleContent;
import com.lam.liveamonthapp.domain.schedule.entity.ScheduleContentEditor;
import com.lam.liveamonthapp.domain.schedule.repository.core.ScheduleContentRepository;
import com.lam.liveamonthapp.domain.schedule.repository.core.ScheduleRepository;
import com.lam.liveamonthapp.domain.schedule.repository.query.ScheduleQueryRepository;
import com.lam.liveamonthapp.exception.notfound.ScheduleNotFound;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleContentServiceImpl implements ScheduleContentService {
    private final SecurityContextFinder finder;
    private final ScheduleContentRepository scheduleContentRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public PostIdResponse addScheduleContent(Long scheduleId, ScheduleContentCreate request) {
        Schedule schedule = scheduleRepository
                .findById(scheduleId)
                .orElseThrow(ScheduleNotFound::new);
        finder.checkLegalWriterOfPost(schedule);
        return PostIdResponse.of(scheduleContentRepository.save(request.toEntity(schedule)).getId());
    }


    @Override
    @Transactional
    public void editScheduleContent(Long contentId, ScheduleContentEdit request) {
        ScheduleContent scheduleContent = scheduleContentRepository.findById(contentId)
                .orElseThrow(ScheduleNotFound::new);
        finder.checkLegalWriterOfPost(scheduleContent);

        ScheduleContentEditor editor = scheduleContent.toEditor()
                .title(request.getTitle())
                .content(request.getContent())
                .timePeriod(request.getTimePeriod().toEntity())
                .cost(request.getCost())
                .build();

        scheduleContent.toEdit(editor);
    }

    @Override
    @Transactional
    public void deleteScheduleContent(Long contentId) {
        ScheduleContent scheduleContent = scheduleContentRepository.findById(contentId)
                .orElseThrow(ScheduleNotFound::new);
        finder.checkLegalWriterOfPost(scheduleContent);

        scheduleContentRepository.delete(scheduleContent);
    }

    @Override
    public List<ScheduleContentResponse> getScheduleContents(Long scheduleId) {
        return scheduleQueryRepository.getScheduleContents(scheduleId);
    }
}

package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.schedule.entity.ScheduleContentEditor;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentEdit;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleContentRepository;
import teamproject.lam_server.domain.schedule.repository.query.ScheduleQueryRepository;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleContentServiceImpl implements ScheduleContentService {
    private final SecurityContextFinder finder;
    private final ScheduleContentRepository scheduleContentRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final ScheduleRepository scheduleRepository;

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
    @Transactional
    public List<ScheduleContentResponse> getScheduleContents(Long scheduleId) {
        return scheduleQueryRepository.getScheduleContents(scheduleId).stream()
                .map(ScheduleContentResponse::of)
                .collect(Collectors.toList());
    }
}

package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.schedule.dto.editor.ScheduleContentEditor;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.domain.schedule.repository.ScheduleContentRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleQueryRepository;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleContentServiceImpl implements ScheduleContentService {

    private final ScheduleContentRepository scheduleContentRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;

    @Override
    @Transactional
    public void addScheduleContent(Long scheduleId, ScheduleContentEditor request) {
        scheduleContentRepository.addContent(scheduleId, request);
    }

    @Override
    @Transactional
    public void editScheduleContent(Long contentId, ScheduleContentEditor request) {
        ScheduleContent scheduleContent = scheduleContentRepository.findById(contentId)
                .orElseThrow(ScheduleNotFound::new);

        ScheduleContentEditor editor = scheduleContent.toEditor()
                .title(request.getTitle())
                .content(request.getContent())
                .timePeriod(request.getTimePeriod())
                .cost(request.getCost())
                .build();

        scheduleContent.toEdit(editor);
    }

    @Override
    @Transactional
    public void deleteScheduleContent(Long contentId) {
        scheduleContentRepository.deleteContent(contentId);
    }

    @Override
    @Transactional
    public List<ScheduleContentResponse> getScheduleContents(Long scheduleId) {
        return scheduleQueryRepository.getScheduleContents(scheduleId).stream()
                .map(ScheduleContentResponse::of)
                .collect(Collectors.toList());
    }
}

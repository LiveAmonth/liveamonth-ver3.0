package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepositoryImpl;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.editor.ScheduleEditor;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleSimpleCardResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleQueryRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final ScheduleCommentRepositoryImpl commentRepository;
    private final DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

    @Transactional
    public void addSchedule(Long memberId, ScheduleEditor request) {
        scheduleRepository.addSchedule(memberId, request);
    }

    @Override
    @Transactional
    public void editSchedule(Long scheduleId, ScheduleEditor request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFound::new);

        ScheduleEditor editor = schedule.toEditor()
                .title(request.getTitle())
                .period(request.getPeriod())
                .publicFlag(request.isPublicFlag())
                .city(request.getCity())
                .build();

        schedule.edit(editor);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteSchedule(scheduleId);
    }

    @Override
    public CustomPage<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        Page<ScheduleCardResponse> page = scheduleQueryRepository.search(cond, pageable).map(schedule -> ScheduleCardResponse.of(
                schedule,
                CommentResponse.ofSingleEntity(
                        commentRepository.getBestComment(schedule.getId()).orElse(null))
        ));
        return CustomPage.<ScheduleCardResponse>builder()
                .page(page)
                .build();
    }

    @Override
    public List<ScheduleSimpleCardResponse> getScheduleByMember(String loginId, Integer size, Long lastId) {
        return scheduleQueryRepository.getScheduleByMember(loginId, size, lastId).stream()
                .map(ScheduleSimpleCardResponse::of)
                .collect(Collectors.toList());
    }
}

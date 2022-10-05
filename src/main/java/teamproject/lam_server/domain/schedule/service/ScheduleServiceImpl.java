package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepositoryImpl;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleEdit;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleEditor;
import teamproject.lam_server.domain.schedule.repository.ScheduleQueryRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {
    private final SecurityContextFinder finder;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;
    private final ScheduleCommentRepositoryImpl commentRepository;
    private final DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

    @Override
    @Transactional
    public void addSchedule(ScheduleCreate request) {
        scheduleRepository.save(request.toEntity(finder.getLoggedInMember()));
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
                .city(request.getCity())
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
        Page<ScheduleCardResponse> page =
                scheduleQueryRepository
                        .search(cond, spec.getPageable(pageableDTO))
                        .map(mapToScheduleAndComment());

        return CustomPage.<ScheduleCardResponse>builder()
                .page(page)
                .build();
    }

    @Override
    public List<ScheduleCardResponse> getScheduleByMember(Integer size, Long lastId) {
        return scheduleQueryRepository.getScheduleByMember(finder.getLoggedInMemberLoginId(), size, lastId)
                .stream()
                .map(schedule -> ScheduleCardResponse.of(schedule, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleCardResponse> getFollowedSchedules(Integer size, Long lastId) {
        return scheduleQueryRepository
                .getFollowedSchedules(finder.getLoggedInMemberLoginId(), size, lastId)
                .stream()
                .map(mapToScheduleAndComment())
                .collect(Collectors.toList());
    }

    private Function<Schedule, ScheduleCardResponse> mapToScheduleAndComment() {
        return schedule -> ScheduleCardResponse.of(
                schedule,
                CommentResponse.ofBest(
                        commentRepository.getBestComment(schedule.getId()).orElse(null))
        );
    }
}

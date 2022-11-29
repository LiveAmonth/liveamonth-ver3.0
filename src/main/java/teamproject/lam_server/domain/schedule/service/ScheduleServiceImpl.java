package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleEdit;
import teamproject.lam_server.domain.schedule.dto.response.EditableScheduleResponse;
import teamproject.lam_server.domain.schedule.dto.response.MyScheduleResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleEditor;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.domain.schedule.repository.query.ScheduleQueryRepository;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;
import teamproject.lam_server.global.dto.response.CountResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

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

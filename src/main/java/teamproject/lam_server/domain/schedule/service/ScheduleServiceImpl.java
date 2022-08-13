package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleContentRequest;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleRequest;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleDetailResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.domain.schedule.repository.ScheduleContentRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;
    private final MemberRepository memberRepository;
    private final DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

    @Transactional
    public void addSchedule(CreateScheduleRequest request) {
        Member member = memberRepository
                .findById(request.getMemberId())
                .orElseThrow(MemberNotFound::new);

        scheduleRepository.save(request.toEntity(member));
    }

    @Transactional
    public void addScheduleContent(CreateScheduleContentRequest request) {
        Schedule schedule = scheduleRepository
                .findById(request.getScheduleId())
                .orElseThrow(ScheduleNotFound::new);

        ScheduleContent scheduleContent = request.toEntity(schedule);
        schedule.increaseTotalCost(scheduleContent.getCost());

        scheduleContentRepository.save(scheduleContent);
    }

    public Page<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        log.info("pageable={}", pageable.getSort().toString());
        return scheduleRepository.search(cond, pageable).map(ScheduleCardResponse::of);
    }

    public ScheduleDetailResponse getScheduleDetails(Long scheduleId) {
        List<ScheduleContent> contents = scheduleContentRepository.getScheduleContents(scheduleId);
        Schedule schedule = contents.stream().findAny().orElseThrow().getSchedule();
        return ScheduleDetailResponse.of(schedule, contents);
    }
}

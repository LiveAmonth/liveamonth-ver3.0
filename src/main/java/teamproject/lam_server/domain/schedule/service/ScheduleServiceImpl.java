package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.domain.schedule.dto.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.domain.schedule.repository.ScheduleContentRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.exception.notfound.ScheduleNotFound;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;
    private final MemberRepository memberRepository;
    private final DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

    @Transactional
    public void addSchedule(ScheduleCreate request) {
        Member member = memberRepository
                .findById(request.getMemberId())
                .orElseThrow(MemberNotFound::new);

        scheduleRepository.save(request.toEntity(member));
    }

    @Transactional
    public void addScheduleContent(ScheduleContentCreate request) {
        Schedule schedule = scheduleRepository
                .findById(request.getScheduleId())
                .orElseThrow(ScheduleNotFound::new);

        ScheduleContent scheduleContent = request.toEntity(schedule);
        schedule.increaseTotalCost(scheduleContent.getCost());

        scheduleContentRepository.save(scheduleContent);
    }

    public Page<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        return scheduleRepository.search(cond, pageable).map(ScheduleCardResponse::of);
    }

    public List<ScheduleContentResponse> getScheduleContents(Long scheduleId){
        return scheduleContentRepository.getScheduleContents(scheduleId).stream()
                .map(ScheduleContentResponse::of)
                .collect(Collectors.toList());
    }
}

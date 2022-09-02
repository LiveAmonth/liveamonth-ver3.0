package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepositoryImpl;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleContentRequest;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleRequest;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleSimpleCardResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
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
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;
    private final MemberRepository memberRepository;
    private final ScheduleCommentRepositoryImpl commentRepository;
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

        scheduleContentRepository.save(request.toEntity(schedule));
    }

    public Page<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        Page<Schedule> search = scheduleRepository.search(cond, pageable);
        return search.map(schedule -> ScheduleCardResponse.of(
                schedule,
                CommentResponse.ofSingleEntity(
                        commentRepository.getBestComment(schedule.getId()).orElse(null))
        ));
    }

    @Transactional
    public List<ScheduleContentResponse> getScheduleContents(Long scheduleId) {
        return scheduleRepository.getScheduleContents(scheduleId).stream()
                .map(ScheduleContentResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleSimpleCardResponse> getScheduleByMember(String loginId) {
        return scheduleRepository.getScheduleByMember(loginId).stream()
                .map(ScheduleSimpleCardResponse::of)
                .collect(Collectors.toList());
    }

}

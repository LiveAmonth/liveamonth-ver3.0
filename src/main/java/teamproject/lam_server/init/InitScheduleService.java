package teamproject.lam_server.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleContentRequest;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleRequest;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.domain.schedule.repository.ScheduleContentRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.util.JsonUtil;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitScheduleService {
    private static final String SCHEDULE = "schedule";
    private static final String SCHEDULE_CONTENT = "scheduleContent";
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;


    @Transactional
    public void initScheduleData() {
        scheduleRepository.saveAll(
                JsonUtil.jsonArrayToList(SCHEDULE, CreateScheduleRequest.class).stream()
                        .map(this::mapToSchedule)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public void initScheduleContentData() {
        scheduleContentRepository.saveAll(
                JsonUtil.jsonArrayToList(SCHEDULE_CONTENT, CreateScheduleContentRequest.class).stream()
                        .map(this::mapToScheduleContent)
                        .collect(Collectors.toList())
        );
    }

    private Schedule mapToSchedule(CreateScheduleRequest request) {
        Member member = memberRepository.findAll().get((int) (request.getMemberId() - 1));
        return request.toEntity(member);
    }

    private ScheduleContent mapToScheduleContent(CreateScheduleContentRequest request) {
        Schedule schedule = scheduleRepository.findAll().get((int) (request.getScheduleId() - 1));
        ScheduleContent scheduleContent = request.toEntity(schedule);
        schedule.increaseTotalCost(scheduleContent.getCost());
        return scheduleContent;
    }
}

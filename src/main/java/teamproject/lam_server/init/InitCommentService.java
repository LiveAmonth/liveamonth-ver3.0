package teamproject.lam_server.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleContentRequest;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class InitCommentService {

    private static final String SCHEDULE_COMMENT = "scheduleComment";
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleCommentRepository scheduleCommentRepository;


    @Transactional
    public void initScheduleCommentData() {
//        scheduleRepository.saveAll(
//                JsonUtil.jsonArrayToList(SCHEDULE_COMMENT, WriteCommentRequest.class).stream()
//                        .map(this::mapToSchedule)
//                        .collect(Collectors.toList())
//        );
    }


    private ScheduleComment mapToSchedule(WriteCommentRequest request) {
        Member member = memberRepository.findAll().get((int) (request.getMemberId() - 1));
        Schedule schedule = scheduleRepository.findAll().get(0);
        return request.toScheduleEntity(member).schedule(schedule).build();
    }

    private ScheduleContent mapToScheduleContent(CreateScheduleContentRequest request) {
        Schedule schedule = scheduleRepository.findAll().get((int) (request.getScheduleId() - 1));
        return request.toEntity(schedule);
    }
}

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
import teamproject.lam_server.util.JsonUtil;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitCommentService {

    private static final String SCHEDULE_COMMENT = "scheduleComment";
    private static final String SCHEDULE_REPLY_COMMENT = "scheduleReplyComment";
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleCommentRepository scheduleCommentRepository;


    @Transactional
    public void initScheduleCommentData() {
        scheduleCommentRepository.saveAll(
                JsonUtil.jsonArrayToList(SCHEDULE_COMMENT, WriteCommentRequest.class).stream()
                        .map(this::mapToScheduleComment)
                        .collect(Collectors.toList())
        );
    }
    @Transactional
    public void initScheduleReplyCommentData() {
        scheduleCommentRepository.saveAll(
                JsonUtil.jsonArrayToList(SCHEDULE_REPLY_COMMENT, WriteCommentRequest.class).stream()
                        .map(this::mapToScheduleReplyComment)
                        .collect(Collectors.toList())
        );
    }


    private ScheduleComment mapToScheduleComment(WriteCommentRequest request) {
        Member member = memberRepository.findAll().get(0);
        Schedule schedule = scheduleRepository.findAll().stream().findAny().get();
        return request.toScheduleEntity(member).schedule(schedule).build();
    }
    private ScheduleComment mapToScheduleReplyComment(WriteCommentRequest request) {
        int parentCommentSize = (int) scheduleCommentRepository.count();
        Member member = memberRepository.findAll().get(1);
        Schedule schedule = scheduleRepository.findAll().stream().findAny().get();
        ScheduleComment comment = scheduleCommentRepository.findAll().stream()
                .skip((int) (parentCommentSize * Math.random()))
                .findAny().get();
        return request.toScheduleEntity(member).schedule(schedule).parent(comment).build();
    }

    private ScheduleContent mapToScheduleContent(CreateScheduleContentRequest request) {
        Schedule schedule = scheduleRepository.findAll().get((int) (request.getScheduleId() - 1));
        return request.toEntity(schedule);
    }
}

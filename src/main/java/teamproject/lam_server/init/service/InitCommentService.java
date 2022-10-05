package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.util.JsonUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitCommentService {
    private static final String SCHEDULE_COMMENT = "scheduleComment";
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleCommentRepository scheduleCommentRepository;

    @Transactional
    public void initScheduleCommentData() {
        List<CommentCreate> editors = JsonUtil.jsonArrayToList(SCHEDULE_COMMENT, CommentCreate.class);
        long count = scheduleCommentRepository.count();
        for (CommentCreate request : editors) {
            Member member = memberRepository.findAll().get((int) (2 * Math.random()));

            Long scheduleId = request.getParentId() == null
                    ? scheduleRepository.findAll().get((int) ((count - 1) * Math.random())).getId()
                    : scheduleCommentRepository.findById(request.getParentId()).get().getSchedule().getId();

            scheduleCommentRepository.write(member, scheduleId, request);
        }
    }
}

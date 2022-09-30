package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.util.JsonUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitCommentService {
    private static final String SCHEDULE_COMMENT = "scheduleComment";
    private static final String SCHEDULE_REPLY_COMMENT = "scheduleReplyComment";
    private final MemberRepository memberRepository;
    private final ScheduleCommentRepository scheduleCommentRepository;

    @Transactional
    public void initScheduleCommentData() {
        List<CommentCreate> editors = JsonUtil.jsonArrayToList(SCHEDULE_COMMENT, CommentCreate.class);
        for (CommentCreate request : editors) {
            Member member = memberRepository.findAll().get((int) (2 * Math.random()));
            scheduleCommentRepository.write(member, request);
        }
    }

    @Transactional
    public void initScheduleReplyCommentData() {
        List<CommentCreate> editors = JsonUtil.jsonArrayToList(SCHEDULE_REPLY_COMMENT, CommentCreate.class);
        for (CommentCreate request : editors) {
            Member member = memberRepository.findAll().get((int) (2 * Math.random()));
            scheduleCommentRepository.write(member, request);
        }
    }
}

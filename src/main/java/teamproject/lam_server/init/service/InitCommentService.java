package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.CommentEditor;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.util.JsonUtil;

import java.util.List;

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
        List<CommentEditor> editors = JsonUtil.jsonArrayToList(SCHEDULE_COMMENT, CommentEditor.class);
        for (CommentEditor commentEditor : editors) {
            Member member = memberRepository.findAll().get((int) (2 * Math.random()));
            Schedule schedule = scheduleRepository.findAll().stream().findAny().get();
            commentEditor.setContentId(schedule.getId());
            scheduleCommentRepository.write(member, commentEditor);
        }
    }

    @Transactional
    public void initScheduleReplyCommentData() {
        List<CommentEditor> editors = JsonUtil.jsonArrayToList(SCHEDULE_REPLY_COMMENT, CommentEditor.class);
        for (CommentEditor commentEditor : editors) {
            int parentCommentSize = (int) scheduleCommentRepository.count();
            Member member = memberRepository.findAll().get((int) (2 * Math.random()));
            Schedule schedule = scheduleRepository.findAll().stream().findAny().get();
            ScheduleComment comment = scheduleCommentRepository.findAll().stream()
                    .skip((int) (parentCommentSize * Math.random()))
                    .findAny().get();
            commentEditor.setContentId(schedule.getId());
            commentEditor.setCommentId(comment.getId());
            scheduleCommentRepository.write(member, commentEditor);
        }
    }
}

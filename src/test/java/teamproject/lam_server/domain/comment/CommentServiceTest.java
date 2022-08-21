package teamproject.lam_server.domain.comment;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.comment.service.CommentService;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;

@SpringBootTest
@Transactional
@ActiveProfiles({"local", "oauth2"})
@Rollback
@Slf4j
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    ScheduleCommentRepository scheduleCommentRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    MemberRepository memberRepository;

    private Schedule schedule;
    private Member member;

    @BeforeEach
    void setUp() {
        this.schedule = scheduleRepository.findAll().stream().findAny().get();
        this.member = memberRepository.findAll().stream().findAny().get();

    }

    @Test
    @DisplayName("스케줄 댓글 저장")
    void test1() {
        // given
        WriteCommentRequest request = new WriteCommentRequest();
        request.setMemberId(member.getId());
        request.setComment("테스트 댓글");

        // when
        Long saveCommentId = commentService.writeScheduleComment(schedule.getId(), null, request);

        ScheduleComment findComment = scheduleCommentRepository.findById(saveCommentId).get();

        // then
        Assertions.assertThat(findComment.getMember().getId()).isEqualTo(member.getId());
        Assertions.assertThat(findComment.getContent()).isEqualTo("테스트 댓글");
    }

    @Test
    @DisplayName("스케줄 대댓글 저장")
    void test2() {
        // given
        WriteCommentRequest request = new WriteCommentRequest();
        request.setMemberId(member.getId());
        request.setComment("테스트 댓글");

        // when
        Long parentCommentId = commentService.writeScheduleComment(schedule.getId(), null, request);
        Long childCommentId = commentService.writeScheduleComment(schedule.getId(), parentCommentId, request);

        ScheduleComment findComment = scheduleCommentRepository.findById(childCommentId).get();

        // then
        Assertions.assertThat(findComment.getMember().getId()).isEqualTo(member.getId());
        Assertions.assertThat(findComment.getContent()).isEqualTo("테스트 댓글");
        Assertions.assertThat(findComment.getParent().getId()).isEqualTo(parentCommentId);
    }

}

package teamproject.lam_server.domain.comment;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.service.AuthService;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.comment.service.CommentService;
import teamproject.lam_server.domain.member.dto.request.LoginRequest;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.paging.PageableDTO;

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

    @Autowired
    AuthService authService;

    private Schedule schedule;
    private Member member;

    @BeforeEach
    void setUp() {
        this.schedule = scheduleRepository.findAll().stream().findFirst().get();
        this.member = memberRepository.findAll().stream().findAny().get();
    }

    @Test
    @DisplayName("스케줄 댓글 저장")
    void test1() {
        // given
        WriteCommentRequest request = new WriteCommentRequest();
//        request.setMemberId(member.getId());
        request.setComment("테스트 댓글");

        // when
        LoginRequest request1 = new LoginRequest();
        Long saveCommentId = commentService.writeScheduleComment(token, schedule.getId(), null, request);

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
//        request.setMemberId(member.getId());
        request.setComment("테스트 댓글");

        // when
        Long parentCommentId = commentService.writeScheduleComment(token, schedule.getId(), null, request);
        Long childCommentId = commentService.writeScheduleComment(token, schedule.getId(), parentCommentId, request);

        ScheduleComment findComment = scheduleCommentRepository.findById(childCommentId).get();

        // then
        Assertions.assertThat(findComment.getMember().getId()).isEqualTo(member.getId());
        Assertions.assertThat(findComment.getContent()).isEqualTo("테스트 댓글");
        Assertions.assertThat(findComment.getParent().getId()).isEqualTo(parentCommentId);
    }

    @Test
    @DisplayName("스케줄 댓글 조회")
    void test3() {
        // given
        PageableDTO pageableDTO = new PageableDTO(0, 20, null);

        // when
        Page<CommentResponse> scheduleComments = commentService.getScheduleComments(this.schedule.getId(), pageableDTO);
        log.info("scheduleComments={}", scheduleComments);

        // then
    }

}

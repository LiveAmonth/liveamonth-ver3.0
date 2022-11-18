package teamproject.lam_server.repository.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.response.BaseCommentResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentProfileResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentReplyResponse;
import teamproject.lam_server.domain.comment.entity.QReviewComment;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.repository.ReviewCommentQueryRepository;
import teamproject.lam_server.domain.comment.repository.ReviewCommentRepository;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.repository.core.ReviewRepository;
import teamproject.lam_server.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static teamproject.lam_server.domain.comment.entity.QReviewComment.reviewComment;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class CommentTest {

    @Autowired
    ReviewCommentQueryRepository reviewCommentQueryRepository;
    @Autowired
    ReviewCommentRepository commentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JPAQueryFactory queryFactory;

    Review savedReview;

    @BeforeAll
    void setUp() {
        MemberCreate memberCreate =
                MemberCreate.builder()
                        .loginId("member")
                        .password("memberPassword1!")
                        .name("memberName")
                        .nickname("memberNickname")
                        .email("member@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        Member member = memberRepository.save(memberCreate.toEntity(passwordEncoder));

        ReviewCreate create = ReviewCreate.builder()
                .title("review title")
                .content("review content")
                .category(ReviewCategory.SE_REVIEW.getCode())
                .tags(Collections.emptySet())
                .build();
        savedReview = reviewRepository.save(create.toEntity(member, Collections.emptySet()));
        for (int i = 0; i < 10000; i++) {
            CommentCreate request = CommentCreate.builder()
                    .comment("댓글" + i)
                    .build();
            commentRepository.write(member.getLoginId(), member.getId(), savedReview.getId(), request);
        }

        for (int i = 1; i < 1000; i++) {
            for (int j = 1; j < 11; j++) {
                CommentCreate request = CommentCreate.builder()
                        .comment("대댓글" + j)
                        .parentId((long) i)
                        .build();
                commentRepository.write(member.getLoginId(), member.getId(), savedReview.getId(), request);
            }
        }
    }

    @Test
    @DisplayName("댓글, 대댓글 따로 조회 후 매핑")
    void get_comment_mapping() {
        // given
        long startTime = System.currentTimeMillis();
        Pageable pageable = PageRequest.of(0, 10);

        List<ReviewComment> elements = queryFactory.select(reviewComment)
                .from(reviewComment)
                .leftJoin(reviewComment.review, review).fetchJoin()
                .leftJoin(reviewComment.member, member).fetchJoin()
                .where(
                        review.id.eq(savedReview.getId()),
                        reviewComment.parent.id.isNull()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewComment.id.desc())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory.select(reviewComment.count())
                .from(reviewComment)
                .where(
                        review.id.eq(savedReview.getId()),
                        reviewComment.parent.id.isNull()
                );

        Page<ReviewComment> result = PageableExecutionUtils.getPage(
                elements,
                pageable,
                countQuery::fetchOne);


        List<ReviewComment> reviewCommentReplies = result.getNumberOfElements() != 0
                ? getReviewCommentReplies(result.getContent())
                : Collections.emptyList();

        Page<TestCommentResponse> page = result.map(comment -> mapToCommentResponse(
                TestCommentResponse.of(comment),
                comment.getId(),
                reviewCommentReplies));

        long stopTime = System.currentTimeMillis();

        // when
        log.info("경과시간={}", stopTime - startTime);

        // then
    }



    @Test
    @DisplayName("댓글 조회 인덱싱 방법")
    void get_comment_index() {
        // given
        long startTime = System.currentTimeMillis();

        Pageable pageable = PageRequest.of(0, 10);

        List<Long> ids = queryFactory.select(reviewComment.id)
                .from(reviewComment)
                .join(reviewComment.review, review)
                .where(
                        reviewComment.parent.id.isNull(),
                        review.id.eq(savedReview.getId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewComment.id.desc())
                .fetch();

        QReviewComment replyComment = new QReviewComment("replyComment");

        List<CommentDto> contents = queryFactory.select(
                        Projections.constructor(CommentDto.class,
                                reviewComment.id,
                                reviewComment.comment,
                                Projections.fields(CommentProfileResponse.class,
                                        member.nickname,
                                        member.image
                                ),
                                reviewComment.createdDate,
                                reviewComment.numberOfLikes,
                                reviewComment.numberOfDislikes,
                                Projections.list(
                                        Projections.constructor(CommentReplyDto.class,
                                                replyComment.id,
                                                replyComment.comment,
                                                Projections.fields(CommentProfileResponse.class,
                                                        member.nickname,
                                                        member.image
                                                ),
                                                replyComment.createdDate,
                                                replyComment.numberOfLikes,
                                                replyComment.numberOfDislikes,
                                                replyComment.parent.id
                                        )
                                )
                        )
                ).from(reviewComment)
                .join(reviewComment.member, member)
                .join(reviewComment.children, replyComment)
                .where(reviewComment.id.in(ids))
                .fetch();

        // when
        long stopTime = System.currentTimeMillis();
        log.info("[INDEX 방법] 경과시간={}", stopTime - startTime);

        // then
    }

    List<ReviewComment> getReviewCommentReplies(List<ReviewComment> comments) {
        Long from = comments.get(comments.size() - 1).getId();
        Long to = comments.get(0).getId();
        return queryFactory.selectFrom(reviewComment)
                .leftJoin(reviewComment.review, review).fetchJoin()
                .leftJoin(reviewComment.member, member).fetchJoin()
                .leftJoin(reviewComment.parent).fetchJoin()
                .where(
                        review.id.eq(savedReview.getId()),
                        reviewComment.parent.id.isNotNull(),
                        reviewComment.parent.id.between(from, to)
                )
                .orderBy(reviewComment.id.desc())
                .fetch();
    }

    TestCommentResponse mapToCommentResponse(TestCommentResponse.TestCommentResponseBuilder builder,
                                             Long parentId,
                                             List<ReviewComment> children) {
        return builder.commentReplies(
                children.stream()
                        .filter(comment -> comment.getParent().getId().equals(parentId))
                        .map(comment -> TestCommentResponse.ofReply(parentId, comment))
                        .collect(Collectors.toList())
        ).build();
    }


    @Getter
    @Builder
    static class TestCommentResponse {
        private Long commentId;
        private Long parentId;
        private String comment;
        private CommentProfileResponse profile;
        private List<TestCommentResponse> commentReplies;
        private String elapsedTime;
        private long numberOfLikes;
        private long numberOfDislikes;

        static TestCommentResponse.TestCommentResponseBuilder of(ReviewComment comment) {
            return TestCommentResponse.builder()
                    .commentId(comment.getId())
                    .comment(comment.getComment())
                    .profile(new CommentProfileResponse(comment.getMember().getNickname(), comment.getMember().getImage()))
                    .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                    .numberOfLikes(comment.getNumberOfLikes())
                    .numberOfDislikes(comment.getNumberOfDislikes());
        }

        static TestCommentResponse ofReply(Long parentId, ReviewComment comment) {
            return TestCommentResponse.builder()
                    .commentId(comment.getId())
                    .comment(comment.getComment())
                    .parentId(parentId)
                    .profile(new CommentProfileResponse(comment.getMember().getNickname(), comment.getMember().getImage()))
                    .elapsedTime(DateTimeUtil.calcTimeBefore(comment.getCreatedDate()))
                    .numberOfLikes(comment.getNumberOfLikes())
                    .numberOfDislikes(comment.getNumberOfDislikes())
                    .commentReplies(Collections.emptyList())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    static class CommentDto extends BaseCommentResponse {
        private List<CommentReplyResponse> commentReplies;

        public CommentDto(
                Long commentId,
                String comment,
                CommentProfileResponse profile,
                LocalDateTime createdDate,
                Integer numberOfLikes,
                Integer numberOfDislikes,
                List<CommentReplyResponse> commentReplies) {
            super(commentId, comment, profile, createdDate, numberOfLikes, numberOfDislikes);
            this.commentReplies = commentReplies;
        }
    }


    @Getter
    @NoArgsConstructor
    static class CommentReplyDto extends BaseCommentResponse {
        private Long parentId;

        public CommentReplyDto(
                Long commentId,
                String comment,
                CommentProfileResponse profile,
                LocalDateTime createdDate,
                Integer numberOfLikes,
                Integer numberOfDislikes,
                Long parentId) {
            super(commentId, comment, profile, createdDate, numberOfLikes, numberOfDislikes);
            this.parentId = parentId;
        }
    }
}

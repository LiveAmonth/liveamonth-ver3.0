package teamproject.lam_server.repository.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import teamproject.lam_server.jdbc.member.MemberJdbcRepository;
import teamproject.lam_server.jdbc.review.ReviewJdbcRepository;
import teamproject.lam_server.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class CommentRepositoryTest {

    @Autowired
    ReviewCommentQueryRepository reviewCommentQueryRepository;
    @Autowired
    ReviewCommentTestRepository reviewCommentTestRepository;
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
    @Autowired
    MemberJdbcRepository memberJdbcRepository;
    @Autowired
    ReviewJdbcRepository reviewJdbcRepository;

    Review savedReview;
    Member savedMember;

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
        savedMember = memberRepository.save(memberCreate.toEntity(passwordEncoder));

        ReviewCreate create = ReviewCreate.builder()
                .title("review title")
                .content("review content")
                .category(ReviewCategory.SE_REVIEW.getCode())
                .tags(Collections.emptySet())
                .build();
        savedReview = reviewRepository.save(create.toEntity(savedMember, Collections.emptySet()));
    }

    @Test
    @DisplayName("댓글 저장 성능 비교")
    void compare_write_comment() {
        List<String> methods = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        int iterations = 50;
        List<MemberCreate> memberCreates = new ArrayList<>();
        List<ReviewCreate> reviewCreates = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            MemberCreate memberCreate =
                    MemberCreate.builder()
                            .loginId("member" + i)
                            .password("memberPassword1!")
                            .name("memberName" + i)
                            .nickname("memberNickname" + i)
                            .email("member" + i + "@liveamonth.com")
                            .birth(LocalDate.now().minusDays(1))
                            .gender(GenderType.MALE.name())
                            .build();
            memberCreates.add(memberCreate);

            ReviewCreate reviewCreate = ReviewCreate.builder()
                    .title("review title")
                    .content("review content")
                    .category(ReviewCategory.SE_REVIEW.getCode())
                    .tags(Collections.emptySet())
                    .build();
            reviewCreates.add(reviewCreate);
        }
        memberJdbcRepository.batchInsert(memberCreates);
        reviewJdbcRepository.batchReviewInsert(reviewCreates, savedMember.getId());

        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();

            Member findMember = memberRepository.findById(savedMember.getId()).get();
            Review findReview = reviewRepository.findById(savedReview.getId()).get();
            ReviewComment reviewComment = ReviewComment.builder()
                    .content("crud repository comment")
                    .member(findMember)
                    .review(findReview)
                    .build();
            commentRepository.save(reviewComment);
            long stopTime = System.currentTimeMillis();
            long crudTime = stopTime - startTime;

            startTime = System.currentTimeMillis();
            CommentCreate request = CommentCreate.builder()
                    .comment("native query comment")
                    .build();
            reviewCommentTestRepository.write(savedMember.getLoginId(), savedMember.getId(), savedReview.getId(), request);
            stopTime = System.currentTimeMillis();
            long queryTime = stopTime - startTime;

            methods.add(crudTime < queryTime ? "CRUD" : "Native");
            times.add(Math.abs(crudTime - queryTime));
        }

        log.info("============== 결과 ================");
        for (int i = 0; i < iterations; i++) {
            log.info("더 빠른 방식={}, 시간차={}", methods.get(i), times.get(i));
        }

    }

    @Test
    @DisplayName("댓글 조회 성능 비교")
    void compare_get_comment() {
        // given
        List<String> methods = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        int iterations = 50;
        long startTime;
        long stopTime;
        long streamTime;
        long indexTime;

        Pageable pageable = PageRequest.of(0, 10);

        // when
        for (int i = 0; i < iterations; i++) {
            // 스트림을 이용한 방식
            startTime = System.currentTimeMillis();
            List<TestCommentResponse> commentsByStream = getCommentsByStream(pageable);
            stopTime = System.currentTimeMillis();
            streamTime = stopTime - startTime;

            // 인덱스를 이용한 방식
            startTime = System.currentTimeMillis();
            List<CommentDto> commentsByIndex = getCommentsByIndex(pageable);
            stopTime = System.currentTimeMillis();
            indexTime = stopTime - startTime;

            // then
            Assertions.assertThat(commentsByStream.size()).isEqualTo(commentsByIndex.size());
            methods.add(streamTime < indexTime ? "Stream" : "Covering Index");
            times.add(Math.abs(streamTime - indexTime));
        }

        log.info("============== 결과 ================");
        for (int i = 0; i < iterations; i++) {
            log.info("더 빠른 방식={}, 시간차={}", methods.get(i), times.get(i));
        }
    }

    private List<TestCommentResponse> getCommentsByStream(Pageable pageable) {
        List<ReviewComment> contents = queryFactory.select(reviewComment)
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


        List<ReviewComment> reviewCommentReplies = getReviewCommentReplies(contents);

        return contents.stream().map(comment -> mapToCommentResponse(
                        TestCommentResponse.of(comment),
                        comment.getId(),
                        reviewCommentReplies))
                .collect(Collectors.toList());
    }

    private List<CommentDto> getCommentsByIndex(Pageable pageable) {
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

        return queryFactory.select(
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

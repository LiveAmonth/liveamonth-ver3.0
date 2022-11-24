package teamproject.lam_server.repository.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.exception.notfound.ReviewNotFound;
import teamproject.lam_server.jdbc.comment.CommentJdbcRepository;
import teamproject.lam_server.jdbc.member.MemberJdbcRepository;
import teamproject.lam_server.jdbc.review.ReviewJdbcRepository;
import teamproject.lam_server.util.DateTimeUtil;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static teamproject.lam_server.domain.comment.entity.QReviewComment.reviewComment;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;
import static teamproject.lam_server.utils.ResultUtils.getPerformanceImprovementRate;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
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
    @Autowired
    CommentJdbcRepository commentJdbcRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("댓글 저장 성능 비교")
    void compare_write_comment() {
        // given
        Member member = createMember();
        Review review = createReview(member);

        List<MemberCreate> memberCreates = new ArrayList<>();
        List<ReviewCreate> reviewCreates = new ArrayList<>();
        int count = 100000;
        for (int i = 0; i < count; i++) {
            memberCreates.add(MemberCreate.builder()
                    .loginId("member" + i)
                    .password("memberPassword1!")
                    .name("memberName" + i)
                    .nickname("memberNickname" + i)
                    .email("member" + i + "@liveamonth.com")
                    .birth(LocalDate.now().minusDays(1))
                    .gender(GenderType.MALE.name())
                    .build());

            reviewCreates.add(ReviewCreate.builder()
                    .title("review title")
                    .content("review content")
                    .category(ReviewCategory.SE_REVIEW.getCode())
                    .tags(Collections.emptySet())
                    .build());
        }
        memberJdbcRepository.batchInsert(memberCreates);
        reviewJdbcRepository.batchReviewInsert(reviewCreates, member.getId());

        em.clear();
        // when
        long startTime = System.currentTimeMillis();
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFound::new);
        Review findReview = reviewRepository.findById(review.getId()).orElseThrow(ReviewNotFound::new);
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
        reviewCommentTestRepository.write(member.getLoginId(), member.getId(), review.getId(), request);
        stopTime = System.currentTimeMillis();
        long queryTime = stopTime - startTime;

        // then
        log.info("== 결과(" + count + "건 기준) ==");
        log.info("dirty checking={}ms", crudTime);
        log.info("native query={}ms", queryTime);
        log.info("더 빠른 방식={}, 성능 개선율={}",
                crudTime < queryTime ? "dirty checking" : "native query",
                getPerformanceImprovementRate(crudTime, queryTime));
    }


    @Test
    @DisplayName("댓글 조회 성능 비교")
    void compare_get_comment() {
        // given
        Member memberEntity = createMember();
        Review reviewEntity = createReview(memberEntity);

        int count = 10000;
        List<CommentCreate> commentCreates = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            commentCreates.add(CommentCreate.builder()
                    .comment("native query comment")
                    .build());
        }
        commentJdbcRepository.batchInsert(commentCreates, memberEntity.getId(), reviewEntity.getId());

        Pageable pageable = PageRequest.of(0, 10);

        // when
        // 스트림을 이용한 방식
        long startTime = System.currentTimeMillis();
        List<ReviewComment> contents = queryFactory.select(reviewComment)
                .from(reviewComment)
                .leftJoin(reviewComment.review, review).fetchJoin()
                .leftJoin(reviewComment.member, member).fetchJoin()
                .where(
                        review.id.eq(reviewEntity.getId()),
                        reviewComment.parent.id.isNull()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewComment.id.desc())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory.select(reviewComment.count())
                .from(reviewComment)
                .where(
                        review.id.eq(reviewEntity.getId()),
                        reviewComment.parent.id.isNull()
                );

        List<ReviewComment> reviewCommentReplies = getReviewCommentReplies(contents, reviewEntity.getId());

        List<TestCommentResponse> collect = contents.stream().map(comment -> mapToCommentResponse(
                        TestCommentResponse.of(comment),
                        comment.getId(),
                        reviewCommentReplies))
                .collect(Collectors.toList());

        long stopTime = System.currentTimeMillis();
        long streamTime = stopTime - startTime;

        // 인덱스를 이용한 방식
        startTime = System.currentTimeMillis();
        List<Long> ids = queryFactory.select(reviewComment.id)
                .from(reviewComment)
                .join(reviewComment.review, review)
                .where(
                        reviewComment.parent.id.isNull(),
                        review.id.eq(reviewEntity.getId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewComment.id.desc())
                .fetch();

        QReviewComment replyComment = new QReviewComment("replyComment");

        queryFactory.select(
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
        stopTime = System.currentTimeMillis();
        long indexTime = stopTime - startTime;

        // then
        log.info("== 결과(" + count + "건 기준) ==");
        log.info("basic paging={}ms", streamTime);
        log.info("covering index={}ms", indexTime);
        log.info("더 빠른 방식={}, 성능 개선율={}",
                streamTime < indexTime ? "basic paging" : "covering index",
                getPerformanceImprovementRate(streamTime, indexTime));
    }

    Review createReview(Member member) {
        ReviewCreate create = ReviewCreate.builder()
                .title("review title")
                .content("review content")
                .category(ReviewCategory.SE_REVIEW.getCode())
                .tags(Collections.emptySet())
                .build();
        return reviewRepository.save(create.toEntity(member, Collections.emptySet()));
    }

    Member createMember() {
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
        return memberRepository.save(memberCreate.toEntity(passwordEncoder));
    }

    List<ReviewComment> getReviewCommentReplies(List<ReviewComment> comments, Long reviewId) {
        Long from = comments.get(comments.size() - 1).getId();
        Long to = comments.get(0).getId();
        return queryFactory.selectFrom(reviewComment)
                .leftJoin(reviewComment.review, review).fetchJoin()
                .leftJoin(reviewComment.member, member).fetchJoin()
                .leftJoin(reviewComment.parent).fetchJoin()
                .where(
                        review.id.eq(reviewId),
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

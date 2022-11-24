package teamproject.lam_server.repository.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.repository.core.ReviewRepository;
import teamproject.lam_server.domain.review.repository.core.TagRepository;
import teamproject.lam_server.domain.review.repository.query.ReviewQueryRepository;
import teamproject.lam_server.exception.notfound.ReviewNotFound;
import teamproject.lam_server.jdbc.review.ReviewJdbcRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;
import static teamproject.lam_server.domain.review.entity.QReviewTag.reviewTag;
import static teamproject.lam_server.domain.review.entity.QTag.tag;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class ReviewRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JPAQueryFactory queryFactory;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewJdbcRepository reviewJdbcRepository;
    @Autowired
    ReviewTagTestRepository reviewTagTestRepository;
    @Autowired
    ReviewQueryRepository reviewQueryRepository;
    @Autowired
    TagRepository tagRepository;

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

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            tags.add("tag" + i);
        }
        reviewJdbcRepository.batchTagInsert(tags);
        List<ReviewCreate> reviewCreates = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            reviewCreates.add(ReviewCreate.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .category(ReviewCategory.SE_REVIEW.getCode())
                    .tags(Collections.emptySet())
                    .build());
        }
        reviewJdbcRepository.batchReviewInsert(reviewCreates, savedMember.getId());

        List<Long> reviewIds = new ArrayList<>();
        List<Long> tagIds = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            long randomId = (long) ((Math.random() * 10000) + 1);
            for (int j = 0; j < 5; j++) {
                long temp = (long) ((Math.random() * 10000) + 1);
                if (randomId != temp) {
                    reviewIds.add((long) (i + 1));
                    tagIds.add(temp);
                }
                randomId = temp;
            }
        }
        reviewJdbcRepository.batchReviewTagInsert(reviewIds, tagIds);
    }

    @Test
    @DisplayName("후기글 조회 성능 비교")
    void compare_get_review() {
        // given
        List<String> methods = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        int iterations = 50;
        long startTime;
        long stopTime;
        long entityTime;
        long dtoTime;

        // when
        long count = reviewRepository.count();
        for (int i = 0; i < iterations; i++) {
            long id = (long) ((Math.random() * count) + 1);
            startTime = System.currentTimeMillis();
            getReviewWithEntity(id);
            stopTime = System.currentTimeMillis();
            entityTime = stopTime - startTime;

            startTime = System.currentTimeMillis();
            reviewQueryRepository.getReview(id);
            stopTime = System.currentTimeMillis();
            dtoTime = stopTime - startTime;

            methods.add(entityTime < dtoTime ? "Entity" : "DTO");
            times.add(Math.abs(entityTime - dtoTime));
        }

        // then
        log.info("============== 결과 ================");
        for (int i = 0; i < iterations; i++) {
            log.info("더 빠른 방식={}, 시간차={}", methods.get(i), times.get(i));
        }
    }

    @Test
    @DisplayName("태그 조회 성능 비교")
    void compare_get_tags() {
        // given
        List<String> methods = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        int iterations = 50;
        long startTime;
        long stopTime;
        long nativeQueryTime;
        long queryDslTime;

        // when
        for (int i = 0; i < iterations; i++) {
            startTime = System.currentTimeMillis();
            List<String> tagNamesByNativeQuery = reviewTagTestRepository.findTagNamesById(1L);
            stopTime = System.currentTimeMillis();
            nativeQueryTime = stopTime - startTime;

            startTime = System.currentTimeMillis();
            List<String> tagNamesByQueryDsl = findTagNames(1L);
            stopTime = System.currentTimeMillis();
            queryDslTime = stopTime - startTime;

            methods.add(nativeQueryTime < queryDslTime ? "Native Query" : "Querydsl");
            times.add(Math.abs(nativeQueryTime - queryDslTime));
        }

        // then
        log.info("============== 결과 ================");
        for (int i = 0; i < iterations; i++) {
            log.info("더 빠른 방식={}, 시간차={}", methods.get(i), times.get(i));
        }
    }

    ReviewDetailDto getReviewWithEntity(Long id) {
        Review findReview = Optional.ofNullable(queryFactory.selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(review.id.eq(id))
                .fetchOne()).orElseThrow(ReviewNotFound::new);

        List<String> tags = reviewTagTestRepository.findTagNamesById(id);
        return ReviewDetailDto.of(findReview, tags);
    }

    List<String> findTagNames(Long reviewId) {
        return queryFactory.select(tag.name)
                .from(reviewTag)
                .join(reviewTag.tag, tag)
                .where(reviewTag.review.id.eq(reviewId))
                .fetch();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    static class ReviewDetailDto {
        private Long id;
        private String title;
        private ProfileDto profile;
        private String content;
        private ReviewCategory category;
        private List<String> tags;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd a h:mm", timezone = "Asia/Seoul")
        private LocalDateTime createDateTime;
        private long numberOfHits;
        private long numberOfComments;
        private long numberOfLikes;

        public static ReviewDetailDto of(Review review, List<String> tagNames) {
            return ReviewDetailDto.builder()
                    .id(review.getId())
                    .title(review.getTitle())
                    .profile(ProfileDto.of(review.getMember()))
                    .content(review.getContent())
                    .category(review.getCategory())
                    .tags(tagNames)
                    .createDateTime(review.getCreatedDate())
                    .numberOfHits(review.getNumberOfHits())
                    .numberOfLikes(review.getNumberOfLikes())
                    .numberOfComments(review.getNumberOfComments())
                    .build();
        }
    }

    @Getter
    @Builder
    static class ProfileDto {
        private Long id;
        private String loginId;
        private String nickname;
        private String image;
        private long numberOfReviews;
        private long numberOfSchedules;
        private long numberOfFollowers;
        private long numberOfFollows;

        public static ProfileDto of(Member member) {
            return ProfileDto.builder()
                    .id(member.getId())
                    .loginId(member.getLoginId())
                    .nickname(member.getNickname())
                    .image(member.getImage())
                    .numberOfReviews(member.getNumberOfReviews())
                    .numberOfSchedules(member.getNumberOfSchedules())
                    .numberOfFollowers(member.getNumberOfFollowers())
                    .numberOfFollows(member.getNumberOfFollows())
                    .build();
        }
    }
}
package teamproject.lam_server.repository.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static teamproject.lam_server.utils.ResultUtils.getPerformanceImprovementRate;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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

    @Test
    @DisplayName("후기글 조회 성능 비교")
    void compare_get_review() {
        // given
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
        Member savedMember = memberRepository.save(memberCreate.toEntity(passwordEncoder));
        ReviewCreate reviewCreate = ReviewCreate.builder()
                .title("title")
                .content("content")
                .category(ReviewCategory.SE_REVIEW.getCode())
                .tags(Collections.emptySet())
                .build();
        reviewRepository.save(reviewCreate.toEntity(savedMember, Collections.emptySet()));

        int count = 10000;
        List<ReviewCreate> reviewCreates = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            reviewCreates.add(ReviewCreate.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .category(ReviewCategory.SE_REVIEW.getCode())
                    .tags(Collections.emptySet())
                    .build());
        }
        reviewJdbcRepository.batchReviewInsert(reviewCreates, savedMember.getId());

        // when
        long id = 1L;
        long startTime = System.currentTimeMillis();
        Optional.ofNullable(queryFactory.selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(review.id.eq(id))
                .fetchOne()).orElseThrow(ReviewNotFound::new);

        reviewTagTestRepository.findTagNamesById(id);
        long stopTime = System.currentTimeMillis();
        long entityTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        reviewQueryRepository.getReview(id);
        stopTime = System.currentTimeMillis();
        long dtoTime = stopTime - startTime;

        log.info("== 결과(" + count + "건 기준) ==");
        log.info("Entity 조회={}ms", entityTime);
        log.info("DTO 조회={}ms", dtoTime);
        log.info("더 빠른 방식={}, 성능 개선율={}",
                entityTime < dtoTime ? "Entity 조회" : "DTO 조회",
                getPerformanceImprovementRate(entityTime, dtoTime));
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

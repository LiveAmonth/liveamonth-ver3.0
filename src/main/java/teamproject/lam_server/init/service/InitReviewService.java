package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewTag;
import teamproject.lam_server.domain.review.entity.Tag;
import teamproject.lam_server.domain.review.repository.ReviewRepository;
import teamproject.lam_server.domain.review.repository.ReviewTagRepository;
import teamproject.lam_server.domain.review.repository.TagRepository;
import teamproject.lam_server.util.JsonUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitReviewService {

    private static final String REVIEW = "review";
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final TagRepository tagRepository;
    private final EntityManager em;

    @Transactional
    public void initReviewData() {
        String query = "update review r set r.created_by = :created_by, r.last_modified_by = :last_modified_by where review_id = :id";

        List<ReviewCreate> requests = JsonUtil.jsonArrayToList(REVIEW, ReviewCreate.class);
        for (ReviewCreate request : requests) {
            Member member = memberRepository.findAll().get((int) (2 * Math.random()));
            Set<ReviewTag> tags = request.getTags().stream()
                    .map(tag -> ReviewTag.createReviewTag(tagRepository.findByName(tag)
                            .orElseGet(
                                    () -> tagRepository.save(
                                            Tag.builder().name(tag).build()
                                    )
                            )))
                    .collect(Collectors.toSet());
            Review review = Review.builder()
                    .member(member)
                    .title(request.getTitle())
                    .content(request.getContent())
                    .category(ReviewCategory.valueOf(request.getCategory()))
                    .tags(tags)
                    .build();

            reviewRepository.save(review);
            em.createNativeQuery(query)
                    .setParameter("created_by", member.getLoginId())
                    .setParameter("last_modified_by", member.getLoginId())
                    .setParameter("id", review.getId())
                    .executeUpdate();
        }
    }
}

package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.util.JsonUtil;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitReviewService {

    private static final String REVIEW = "review";
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Transactional
    public void initReviewData() {
        String query = "update review r set r.created_by = :created_by, r.last_modified_by = :last_modified_by where review_id = :id";

        List<ReviewCreate> requests = JsonUtil.jsonArrayToList(REVIEW, ReviewCreate.class);
        for (ReviewCreate request : requests) {
            Member member = memberRepository.findAll().get((int) (2 * Math.random()));
            Review review = Review.builder()
                    .member(member)
                    .title(request.getTitle())
                    .content(request.getContent())
                    .category(request.getCategory())
                    .tags(request.getTags())
                    .build();
            em.persist(review);
            em.createNativeQuery(query)
                    .setParameter("created_by", member.getLoginId())
                    .setParameter("last_modified_by", member.getLoginId())
                    .setParameter("id", review.getId())
                    .executeUpdate();
        }
    }
}

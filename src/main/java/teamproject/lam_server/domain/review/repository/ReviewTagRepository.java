package teamproject.lam_server.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamproject.lam_server.domain.review.entity.ReviewTag;

import java.util.List;
import java.util.Set;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    @Query(value = "select rt.review_id from review_tag rt left join tag t on t.tag_id = rt.tag_id where t.name in :tags group by rt.review_id", nativeQuery = true)
    List<Long> findReviewTagsByTags(@Param("tags") Set<String> tags);

    @Query(value = "select t.name from review_tag rt left join tag t on t.tag_id = rt.tag_id where rt.review_id =:id", nativeQuery = true)
    List<String> findTagNamesById(@Param("id") Long id);

    @Query(value = "select rt from ReviewTag rt join fetch Review r join fetch Tag t where r.id =: reviewId")
    List<ReviewTag> findByReviewId(@Param("reviewId") Long reviewId);
}

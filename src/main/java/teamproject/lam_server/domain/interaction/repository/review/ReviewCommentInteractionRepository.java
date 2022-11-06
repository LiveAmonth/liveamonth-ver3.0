package teamproject.lam_server.domain.interaction.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.entity.review.ReviewCommentInteraction;

import java.util.List;
import java.util.Optional;

public interface ReviewCommentInteractionRepository extends JpaRepository<ReviewCommentInteraction, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into review_comment_interaction (created_date, last_modified_date, from_member_id, to_review_comment_id, state) " +
            "values(now(), now(), :#{#request.from}, :#{#request.to}, :#{#state.name})"
            , nativeQuery = true)
    void interact(@Param("request") InteractionRequest request, @Param("state") InteractionState state);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from review_comment_interaction rcr " +
            "where rcr.from_member_id = :#{#request.from} " +
            "and rcr.to_review_comment_id = :#{#request.to}"
            , nativeQuery = true)
    void cancelInteraction(@Param("request") InteractionRequest request);

    @Query(value = "" +
            "select rcr.state from review_comment_interaction rcr " +
            "where rcr.from_member_id = :#{#request.from} " +
            "and rcr.to_review_comment_id = :#{#request.to}"
            , nativeQuery = true)
    Optional<InteractionState> existsInteraction(@Param("request") InteractionRequest request);

    @Query(value = "" +
            "select * from review_comment_interaction rcr " +
            "inner join review_comment rc on rcr.to_review_comment_id = rc.review_comment_id " +
            "where rcr.from_member_id = :memberId " +
            "and rcr.to_review_comment_id in :ids"
            , nativeQuery = true)
    List<ReviewCommentInteraction> getInteractedComments(@Param("memberId")Long memberId, @Param("ids") List<Long> ids);
}

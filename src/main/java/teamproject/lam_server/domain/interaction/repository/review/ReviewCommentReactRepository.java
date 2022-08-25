package teamproject.lam_server.domain.interaction.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.entity.review.ReviewCommentReact;

import java.util.Optional;

public interface ReviewCommentReactRepository extends JpaRepository<ReviewCommentReact, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into review_comment_react (from_member_id, to_review_comment_id, type) " +
            "values(:#{#request.from}, :#{#request.to}, :type)"
            , nativeQuery = true)
    void react(@Param("request") InteractionRequest request, @Param("type") ReactType type);

    @Modifying
    @Transactional
    @Query(value = "" +
            "delete from review_comment_react " +
            "where from_member_id = :#{#request.from} " +
            "and to_review_comment_id = :#{#request.to} " +
            "and type = :type;"
            , nativeQuery = true)
    void cancelLike(@Param("request") InteractionRequest request, @Param("type") ReactType type);

    @Query(value = "" +
            "select type from review_comment_react " +
            "where from_member_id = :#{#request.from} " +
            "and to_review_comment_id = :#{#request.to};"
            , nativeQuery = true)
    Optional<ReactType> existsReact(@Param("request") InteractionRequest request);
}

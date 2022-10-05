package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.member.entity.Member;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long>, CommentRepository<ReviewComment> {

    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into review_comment (created_date, last_modified_date, created_by, last_modified_by, comment, member_id, review_id, parent_comment_id) " +
            "values(now(), now(), :#{#member.loginId}, :#{#member.loginId}, :#{#request.comment}, :#{#member.id}, :contentId, IFNULL(:#{#request.parentId}, null))"
            , nativeQuery = true)
    void write(@Param("member") Member member, @Param("contentId") Long contentId,  @Param("request") CommentCreate request);
}

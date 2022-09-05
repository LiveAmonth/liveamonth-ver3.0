package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;

public interface ScheduleCommentRepository extends JpaRepository<ScheduleComment, Long>, CommentRepository<ScheduleComment> {

    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule_comment (created_date, last_modified_date, content, member_id, schedule_id, parent_comment_id) " +
            "values(now(), now(), :#{#request.comment}, :memberId, :#{#request.contentId}, IFNULL(:#{#request.commentId}, null))"
            , nativeQuery = true)
    void write(@Param("memberId") Long memberId, @Param("request") WriteCommentRequest request);
}

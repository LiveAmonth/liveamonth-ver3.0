package com.lam.liveamonthapp.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentCreate;
import com.lam.liveamonthapp.domain.comment.entity.ScheduleComment;

public interface ScheduleCommentRepository extends JpaRepository<ScheduleComment, Long> {
    @Modifying
    @Transactional
    @Query(value = "" +
            "insert into schedule_comment (created_date, last_modified_date, created_by, last_modified_by, comment, member_id, schedule_id, parent_comment_id) " +
            "values(now(), now(), :writer, :writer, :#{#request.comment}, :writerId, :contentId, IFNULL(:#{#request.parentId}, null))"
            , nativeQuery = true)
    void write(
            @Param("writer") String writer,
            @Param("writerId") Long writerId,
            @Param("contentId") Long contentId,
            @Param("request") CommentCreate request);
}

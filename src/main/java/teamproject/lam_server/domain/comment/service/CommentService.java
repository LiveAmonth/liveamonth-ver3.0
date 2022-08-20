package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.comment.dto.CommentResponse;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.paging.PageableDTO;

public interface CommentService {
    void writeScheduleComment(@Nullable Long scheduleId, WriteCommentRequest request);

    void writeScheduleChildComment(Long commentId, WriteCommentRequest request);

    @Transactional
    void writeReviewComment(Long reviewId, WriteCommentRequest request);

    @Transactional
    void writeReviewChildComment(Long commentId, WriteCommentRequest request);

    Page<CommentResponse> getScheduleComments(Long scheduleId, PageableDTO pageableDTO);

}

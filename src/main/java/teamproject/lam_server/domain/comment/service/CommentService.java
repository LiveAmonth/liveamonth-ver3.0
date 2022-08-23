package teamproject.lam_server.domain.comment.service;

import org.springframework.data.domain.Page;
import teamproject.lam_server.domain.comment.dto.request.WriteCommentRequest;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.paging.PageableDTO;

public interface CommentService {
    Long writeScheduleComment(String accessToken, Long scheduleId, Long commentId, WriteCommentRequest request);


    void writeReviewComment(String accessToken, Long reviewId, Long commentId, WriteCommentRequest request);


    Page<CommentResponse> getScheduleComments(Long scheduleId, PageableDTO pageableDTO);

}

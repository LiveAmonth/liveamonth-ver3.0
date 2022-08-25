package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.comment.entity.ReviewComment;

import java.util.List;

public interface ReviewCommentRepositoryCustom {

    Page<ReviewComment> getReviewComments(Long scheduleId, Pageable pageable);

    List<ReviewComment> getReviewCommentReplies(Long scheduleId, Long from, Long to);
}

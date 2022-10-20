package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.comment.entity.CommentEntity;

import java.util.List;

public interface CommentRepository<T extends CommentEntity> {

    Page<T> getComments(Long contentId, Pageable pageable);

    List<T> getCommentReplies(Long contentId, Long from, Long to);

    List<T> getBestComments(Long contentId);
}

package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.comment.entity.ReviewComment;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
}

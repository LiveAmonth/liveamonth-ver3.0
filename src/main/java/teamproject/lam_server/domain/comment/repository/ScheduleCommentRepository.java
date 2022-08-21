package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;

public interface ScheduleCommentRepository extends JpaRepository<ScheduleComment, Long>, ScheduleCommentRepositoryCustom {
}

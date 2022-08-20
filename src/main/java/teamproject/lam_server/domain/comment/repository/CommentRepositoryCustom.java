package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;

public interface CommentRepositoryCustom {

    Page<ScheduleComment> getScheduleComments(Long scheduleId, Pageable pageable);
}

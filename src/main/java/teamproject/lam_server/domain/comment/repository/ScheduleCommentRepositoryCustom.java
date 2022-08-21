package teamproject.lam_server.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;

public interface ScheduleCommentRepositoryCustom {

    Page<ScheduleComment> getScheduleComments(Long scheduleId, Pageable pageable);

    void getTest(Long scheduleId, Pageable pageable);
}

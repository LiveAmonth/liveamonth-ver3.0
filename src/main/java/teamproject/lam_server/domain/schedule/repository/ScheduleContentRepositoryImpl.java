package teamproject.lam_server.domain.schedule.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.global.repository.BasicRepository;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;
import static teamproject.lam_server.domain.schedule.entity.QScheduleContent.scheduleContent;

@Repository
@RequiredArgsConstructor
public class ScheduleContentRepositoryImpl extends BasicRepository implements ScheduleContentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public List<ScheduleContent> getScheduleContents(Long scheduleId) {
        return queryFactory.selectFrom(scheduleContent)
                .join(scheduleContent.schedule, schedule)
                .where(
                        scheduleIdEq(scheduleId)
                )
                .fetch();
    }

    private BooleanExpression scheduleIdEq(Long id) {
        return id != null ? schedule.id.eq(id) : null;
    }
}

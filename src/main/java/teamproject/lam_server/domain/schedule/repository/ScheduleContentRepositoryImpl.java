package teamproject.lam_server.domain.schedule.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.util.BasicRepositoryUtil;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;
import static teamproject.lam_server.domain.schedule.entity.QScheduleContent.scheduleContent;

@Repository
@RequiredArgsConstructor
public class ScheduleContentRepositoryImpl extends BasicRepositoryUtil implements ScheduleContentRepositoryCustom{

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

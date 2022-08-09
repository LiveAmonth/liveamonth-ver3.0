package teamproject.lam_server.domain.interaction.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.interaction.entity.ScheduleLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

import static teamproject.lam_server.domain.interaction.entity.QScheduleLike.scheduleLike;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class ScheduleLikeRepositoryImpl implements ScheduleLikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ScheduleLike> findById(Long from, Long to) {
        return Optional.ofNullable(
                queryFactory.selectFrom(scheduleLike)
                        .join(scheduleLike.from, member).fetchJoin()
                        .join(scheduleLike.to, schedule).fetchJoin()
                        .where(
                                fromMemberIdEq(from),
                                toScheduleIdEq(to)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Long getScheduleLikesCount(Long to) {
        return queryFactory.select(scheduleLike.count())
                .join(scheduleLike.to, schedule).fetchJoin()
                .where(
                        toScheduleIdEq(to)
                )
                .fetchOne();
    }

    @Override
    public List<Member> getScheduleLikes(Long to) {
        return queryFactory.select(scheduleLike.from)
                .join(scheduleLike.from, member).fetchJoin()
                .leftJoin(scheduleLike.to, schedule)
                .where(
                        toScheduleIdEq(to)
                )
                .fetch();
    }

    @Override
    public Long getLikedSchedulesCount(Long from) {
        return queryFactory.select(scheduleLike.count())
                .join(scheduleLike.from, member).fetchJoin()
                .where(
                        fromMemberIdEq(from)
                )
                .fetchOne();
    }

    @Override
    public List<Schedule> getLikedSchedules(Long from) {
        return queryFactory.select(scheduleLike.to)
                .leftJoin(scheduleLike.from, member)
                .join(scheduleLike.to, schedule).fetchJoin()
                .where(
                        fromMemberIdEq(from)
                )
                .fetch();
    }

    private BooleanExpression fromMemberIdEq(Long from) {
        return from != null ? member.id.eq(from) : null;
    }

    private BooleanExpression toScheduleIdEq(Long to) {
        return to != null ? schedule.id.eq(to) : null;
    }
}

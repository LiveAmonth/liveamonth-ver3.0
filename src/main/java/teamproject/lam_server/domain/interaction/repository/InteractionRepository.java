package teamproject.lam_server.domain.interaction.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

import static teamproject.lam_server.domain.interaction.entity.schedule.QScheduleLike.scheduleLike;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class InteractionRepository {

    private final JPAQueryFactory queryFactory;

    public boolean existsMemberScheduleLike(InteractionRequest request) {
        return queryFactory.selectOne()
                .from(scheduleLike)
                .leftJoin(scheduleLike.from, member)
                .leftJoin(scheduleLike.to, schedule)
                .where(
                        memberIdEq(request.getFrom()),
                        scheduleIdEq(request.getTo())

                ).fetchFirst() != null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? member.id.eq(memberId) : null;
    }

    private BooleanExpression scheduleIdEq(Long scheduleId) {
        return scheduleId != null ? schedule.id.eq(scheduleId) : null;
    }
}

package teamproject.lam_server.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.member.constants.AccountState;

import static teamproject.lam_server.domain.member.entity.QMember.member;


@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long cleanDeleteById(Long id) {
        return queryFactory
                .delete(member)
                .where(
                        IdEq(id),
                        isDropMember()
                )
                .execute();
    }

    private BooleanExpression IdEq(Long id) {
        return id != null ? member.id.eq(id) : null;
    }

    private BooleanExpression isDropMember() {
        return member.status.eq(AccountState.DROP);
    }
}

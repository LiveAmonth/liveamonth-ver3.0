package teamproject.lam_server.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.member.constants.AccountState;

import java.util.Optional;

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
                        member.id.eq(id),
                        member.status.eq(AccountState.DROP))
                .execute();
    }

    @Override
    public Optional<String> findLoginIdByNameAndEmail(String name, String email) {
        return Optional.ofNullable(queryFactory
                .select(member.loginId)
                .from(member)
                .where(
                        member.name.eq(name),
                        member.email.eq(email)
                )
                .fetchOne());
    }
}

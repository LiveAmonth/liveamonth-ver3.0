package teamproject.lam_server.app.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.constants.CategoryConstants;

import java.util.Optional;

import static teamproject.lam_server.app.member.domain.QMember.member;


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
                        member.status.eq(CategoryConstants.MemberStatus.DROP))
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

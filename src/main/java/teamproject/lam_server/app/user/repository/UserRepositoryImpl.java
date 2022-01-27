package teamproject.lam_server.app.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import groovy.lang.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.constants.CategoryConstants.UserStatus;

import java.util.Optional;

import static teamproject.lam_server.app.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Long cleanDeleteById(Long id) {
        return queryFactory
                .delete(user)
                .where(
                        user.id.eq(id),
                        user.status.eq(UserStatus.DROP))
                .execute();
    }

    @Override
    public Optional<String> findLoginId(String name, String email) {
        return Optional.ofNullable(queryFactory
                .select(user.loginId)
                .from(user)
                .where(
                        user.name.eq(name),
                        user.email.eq(email)
                )
                .fetchOne());
    }
}

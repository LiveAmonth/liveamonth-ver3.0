package teamproject.lam_server.app.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.constants.CategoryConstants.UserStatus;

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

}

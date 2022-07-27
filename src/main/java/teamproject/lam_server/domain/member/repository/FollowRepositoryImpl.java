package teamproject.lam_server.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.member.dto.request.FollowRequest;
import teamproject.lam_server.domain.member.entity.Followers;

import java.util.Optional;

import static teamproject.lam_server.domain.member.entity.QFollowers.followers;
import static teamproject.lam_server.domain.member.entity.QMember.member;


@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Followers> findByLoginId(FollowRequest request) {
        return Optional.ofNullable(
                queryFactory.selectFrom(followers)
                        .join(followers.from, member).fetchJoin()
                        .join(followers.to, member).fetchJoin()
                        .where(
                                followers.from.loginId.eq(request.getFrom()),
                                followers.to.loginId.eq(request.getTo())
                        )
                        .fetchOne()
        );
    }
}

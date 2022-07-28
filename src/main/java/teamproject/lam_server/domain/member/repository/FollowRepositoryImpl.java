package teamproject.lam_server.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.member.entity.Follower;
import teamproject.lam_server.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

import static teamproject.lam_server.domain.member.entity.QFollower.follower;
import static teamproject.lam_server.domain.member.entity.QMember.member;


@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Follower> findByMemberId(Long from, Long to) {
        return Optional.ofNullable(
                queryFactory.selectFrom(follower)
                        .join(follower.from, member).fetchJoin()
                        .join(follower.to, member).fetchJoin()
                        .where(
                                fromMemberIdEq(from),
                                toMemberIdEq(to)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Long getFollowersCount(Long to) {
        return queryFactory.select(follower.count())
                .join(follower.to,member).fetchJoin()
                .where(
                        toMemberIdEq(to)
                )
                .fetchOne();
    }


    @Override
    public List<Member> getFollowers(Long to) {
        return queryFactory.select(follower.from)
                .join(follower.from, member).fetchJoin()
                .leftJoin(follower.to, member)
                .where(
                        toMemberIdEq(to)
                )
                .fetch();
    }
    @Override
    public Long getFollowingsCount(Long from) {
        return queryFactory.select(follower.count())
                .join(follower.from,member).fetchJoin()
                .where(
                        fromMemberIdEq(from)
                )
                .fetchOne();
    }
    @Override
    public List<Member> getFollowings(Long from) {
        return queryFactory.select(follower.to)
                .leftJoin(follower.from, member)
                .join(follower.to, member).fetchJoin()
                .where(
                        fromMemberIdEq(from)
                )
                .fetch();
    }

    private BooleanExpression toMemberIdEq(Long to) {
        return to != null ? follower.to.id.eq(to) : null;
    }

    private BooleanExpression fromMemberIdEq(Long from) {
        return from != null ? follower.from.id.eq(from) : null;
    }
}

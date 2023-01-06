package com.lam.liveamonthapp.domain.member.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.lam.liveamonthapp.domain.member.constants.AccountState;
import com.lam.liveamonthapp.domain.member.dto.request.MemberFindId;
import com.lam.liveamonthapp.domain.member.dto.response.FindIdResponse;
import com.lam.liveamonthapp.domain.member.dto.response.MemberProfileResponse;
import com.lam.liveamonthapp.domain.member.dto.response.SimpleProfileResponse;

import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.dsl.Expressions.asString;
import static org.springframework.util.StringUtils.hasText;
import static com.lam.liveamonthapp.domain.member.entity.QMember.member;


@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Long> getIdByLoginId(String loginId) {
        return Optional.ofNullable(
                queryFactory.select(member.id)
                        .from(member)
                        .where(loginIdEq(loginId), isNotDrop())
                        .fetchOne());
    }

    public Long cleanDeleteById(Long id) {
        return queryFactory
                .delete(member)
                .where(idEq(id), isDropMember())
                .execute();
    }

    public Optional<MemberProfileResponse> getMemberProfile(String loginId) {
        return Optional.ofNullable(
                queryFactory.select(
                                constructor(MemberProfileResponse.class,
                                        member.id,
                                        asString(loginId),
                                        member.nickname,
                                        member.image,
                                        member.name,
                                        member.email,
                                        member.birth,
                                        member.gender,
                                        member.numberOfReviews,
                                        member.numberOfSchedules,
                                        member.numberOfFollowers,
                                        member.numberOfFollows)
                        ).from(member)
                        .where(loginIdEq(loginId), isNotDrop())
                        .fetchOne()
        );

    }

    public Optional<SimpleProfileResponse> getSimpleProfile(String loginId) {
        return Optional.ofNullable(
                queryFactory.select(
                                constructor(SimpleProfileResponse.class,
                                        member.id,
                                        asString(loginId),
                                        member.nickname,
                                        member.image,
                                        member.numberOfReviews,
                                        member.numberOfSchedules,
                                        member.numberOfFollowers,
                                        member.numberOfFollows)
                        ).from(member)
                        .where(loginIdEq(loginId), isNotDrop())
                        .fetchOne()
        );
    }

    public Optional<FindIdResponse> findLoginId(MemberFindId request) {
        return Optional.ofNullable(
                queryFactory.select(
                                constructor(FindIdResponse.class,
                                        member.loginId,
                                        member.createdDate
                                )
                        ).from(member)
                        .where(nameEq(request.getName()),
                                emailEq(request.getEmail()),
                                isNotDrop())
                        .fetchOne()
        );
    }

    private BooleanExpression idEq(Long id) {
        return id != null ? member.id.eq(id) : null;
    }

    private BooleanExpression loginIdEq(String loginId) {
        return hasText(loginId) ? member.loginId.eq(loginId) : null;
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? member.name.eq(name) : null;
    }

    private BooleanExpression emailEq(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }

    private BooleanExpression isDropMember() {
        return member.status.eq(AccountState.DROP);
    }

    private BooleanExpression isNotDrop() {
        return member.status.ne(AccountState.DROP);
    }

}

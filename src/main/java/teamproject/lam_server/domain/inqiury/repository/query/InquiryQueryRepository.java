package teamproject.lam_server.domain.inqiury.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;

import java.util.List;

import static teamproject.lam_server.domain.inqiury.entity.QInquiry.inquiry;

@Repository
@RequiredArgsConstructor
public class InquiryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<InquiryListResponse> getInquiries(Long memberId, Pageable pageable) {
        JPAQuery<Long> countQuery = queryFactory.select(inquiry.count())
                .from(inquiry)
                .where(memberIdEq(memberId));

        List<Long> ids = queryFactory.select(inquiry.id)
                .from(inquiry)
                .where(memberIdEq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<InquiryListResponse> contents = queryFactory.select(
                        Projections.constructor(InquiryListResponse.class,
                                inquiry.id,
                                inquiry.title,
                                inquiry.category,
                                inquiry.isAnswered,
                                inquiry.lastModifiedDate
                        )
                ).from(inquiry)
                .where(idIn(ids))
                .orderBy(inquiry.id.desc())
                .fetch();

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    private BooleanExpression memberIdEq(Long id) {
        return id != null ? inquiry.member.id.eq(id) : null;
    }

    private BooleanExpression idIn(List<Long> ids) {
        return ids != null ? inquiry.id.in(ids) : null;
    }
}

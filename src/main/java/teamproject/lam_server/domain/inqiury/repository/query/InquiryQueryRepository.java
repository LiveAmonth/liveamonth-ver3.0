package teamproject.lam_server.domain.inqiury.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryAnswerResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.global.repository.BasicRepository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.dsl.Expressions.asNumber;
import static teamproject.lam_server.domain.inqiury.entity.QInquiry.inquiry;
import static teamproject.lam_server.domain.inqiury.entity.QInquiryAnswer.inquiryAnswer;

@Repository
@RequiredArgsConstructor
public class InquiryQueryRepository extends BasicRepository {

    private final JPAQueryFactory queryFactory;

    public Page<InquiryListResponse> getInquiries(Long memberId, Pageable pageable) {
        // count query
        JPAQuery<Long> countQuery = queryFactory.select(inquiry.count())
                .from(inquiry)
                .where(memberIdEq(memberId));

        // covering index
        List<Long> ids = queryFactory.select(inquiry.id)
                .from(inquiry)
                .where(memberIdEq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        // result query
        JPAQuery<InquiryListResponse> resultQuery = queryFactory.select(
                        constructor(InquiryListResponse.class,
                                inquiry.id,
                                inquiry.title,
                                inquiry.category,
                                inquiry.isAnswered,
                                inquiry.lastModifiedDate
                        )
                ).from(inquiry)
                .where(idIn(ids))
                .orderBy(inquiry.id.desc());

        return PageableExecutionUtils.getPage(
                fetchIndexingQuery(ids.isEmpty(), resultQuery),
                pageable,
                countQuery::fetchOne);
    }


    public Optional<InquiryResponse> getInquiry(Long id) {
        return Optional.ofNullable(queryFactory.select(
                        constructor(InquiryResponse.class,
                                asNumber(id),
                                inquiry.title,
                                inquiry.content,
                                inquiry.category,
                                inquiry.isAnswered,
                                inquiry.lastModifiedDate,
                                constructor(InquiryAnswerResponse.class,
                                        inquiryAnswer.id,
                                        inquiryAnswer.content,
                                        inquiryAnswer.lastModifiedDate
                                )
                        ))
                .from(inquiry)
                .leftJoin(inquiry.answer, inquiryAnswer)
                .where(inquiryIdEq(id))
                .fetchOne());
    }

    private BooleanExpression memberIdEq(Long id) {
        return id != null ? inquiry.member.id.eq(id) : null;
    }

    private BooleanExpression inquiryIdEq(Long id) {
        return id != null ? inquiry.id.eq(id) : null;
    }

    private BooleanExpression idIn(List<Long> ids) {
        return ids != null ? inquiry.id.in(ids) : null;
    }
}

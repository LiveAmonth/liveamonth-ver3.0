package teamproject.lam_server.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.condition.ReviewSortType;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.editor.ReviewEditor;
import teamproject.lam_server.domain.review.repository.ReviewRepository;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

import static teamproject.lam_server.global.exception.ErrorCode.MEMBER_NOT_FOUND;
import static teamproject.lam_server.global.exception.ErrorCode.REVIEW_NOT_FOUND;
import static teamproject.lam_server.util.BasicServiceUtil.getExceptionSupplier;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final DomainSpec<ReviewSortType> spec = new DomainSpec<>(ReviewSortType.class);

    public List<Review> findTOP5Reviews() {
        return reviewRepository.findTop5ByOrderByViewCountDesc();
    }

    @Override
    @Transactional
    public PostIdResponse write(ReviewCreate request) {
        Member writer = memberRepository.findByNickname(request.getWriter())
                .orElseThrow(getExceptionSupplier(MEMBER_NOT_FOUND));

        Review review = reviewRepository.save(request.toEntity(writer));
        return PostIdResponse.of(review.getId());
    }

    @Override
    public ReviewDetailResponse findById(Long id) {
        return ReviewDetailResponse.of(
                reviewRepository
                        .findById(id)
                        .orElseThrow(getExceptionSupplier(REVIEW_NOT_FOUND))
        );
    }

    @Transactional
    public void edit(Long id, ReviewEdit reviewEdit) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(getExceptionSupplier(REVIEW_NOT_FOUND));

        ReviewEditor.ReviewEditorBuilder editorBuilder = review.toEditor();

        ReviewEditor reviewEditor = editorBuilder.title(reviewEdit.getTitle())
                .reviewCategory(reviewEdit.getReviewCategory())
                .content(reviewEdit.getContent())
                .build();
        review.edit(reviewEditor);
    }

    @Override
    public Page<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        return reviewRepository.search(cond, pageable)
                .map(ReviewListResponse::of);
    }
}

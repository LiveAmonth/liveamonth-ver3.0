package teamproject.lam_server.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.review.constants.ReviewSortType;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewEditor;
import teamproject.lam_server.domain.review.repository.ReviewRepository;
import teamproject.lam_server.exception.notfound.ReviewNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final SecurityContextFinder finder;

    private final ReviewRepository reviewRepository;
    private final DomainSpec<ReviewSortType> spec = new DomainSpec<>(ReviewSortType.class);

    @Transactional
    public void write(ReviewCreate request) {
        reviewRepository.save(request.toEntity(finder.getLoggedInMember()));
    }

    @Transactional
    public void edit(Long id, ReviewEdit request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFound::new);

        ReviewEditor.ReviewEditorBuilder editorBuilder = review.toEditor();

        ReviewEditor reviewEditor = editorBuilder.title(request.getTitle())
                .category(request.getCategory())
                .content(request.getContent())
                .build();
        review.edit(reviewEditor);
    }

    public CustomPage<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        Page<ReviewListResponse> page =
                reviewRepository
                        .search(cond, pageable)
                        .map(ReviewListResponse::of);

        return CustomPage.<ReviewListResponse>builder()
                .page(page)
                .build();
    }

    public List<ReviewListResponse> getReviewByMember(String loginId, Integer size, Long lastId) {
        finder.checkLegalLoginId(loginId);
        return reviewRepository.getReviewByMember(loginId, size, lastId).stream()
                .map(ReviewListResponse::of)
                .collect(Collectors.toList());

    }

    @Transactional
    public ReviewDetailResponse getReview(Long id) {
        return null;
    }
}

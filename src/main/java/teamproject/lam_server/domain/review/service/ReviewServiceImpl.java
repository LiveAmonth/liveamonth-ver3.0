package teamproject.lam_server.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.constants.ReviewSortType;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewEdit;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.dto.response.TagResponse;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewEditor;
import teamproject.lam_server.domain.review.entity.ReviewTag;
import teamproject.lam_server.domain.review.entity.Tag;
import teamproject.lam_server.domain.review.repository.core.ReviewRepository;
import teamproject.lam_server.domain.review.repository.core.TagRepository;
import teamproject.lam_server.domain.review.repository.query.ReviewQueryRepository;
import teamproject.lam_server.exception.notfound.ReviewNotFound;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final SecurityContextFinder finder;
    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final TagRepository tagRepository;
    private final DomainSpec<ReviewSortType> spec = new DomainSpec<>(ReviewSortType.class);

    @Override
    @Transactional
    public PostIdResponse write(String loginId, ReviewCreate request) {
        finder.checkLegalLoginId(loginId);
        Review save = reviewRepository.save(
                request.toEntity(finder.getLoggedInMember(), mapToReviewTags(request.getTags()))
        );
        return PostIdResponse.of(save.getId());
    }

    @Override
    @Transactional
    public void edit(Long id, ReviewEdit request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFound::new);
        finder.checkLegalWriterOfPost(review);

        // 리뷰 태그 삭제
        if (request.getRemovedTags() != null) {
            review.removeTags(reviewQueryRepository.findReviewTagsByIdAndTag(id, request.getRemovedTags()));
        }

        // 리뷰 태그 추가
        if (request.getAddedTags() != null) {
            for (ReviewTag addedTag : mapToReviewTags(request.getAddedTags())) {
                review.addTag(addedTag);
            }
        }

        // 나머지 내용 수정
        ReviewEditor.ReviewEditorBuilder editorBuilder = review.toEditor();
        ReviewEditor reviewEditor = editorBuilder.title(request.getTitle())
                .category(ReviewCategory.valueOf(request.getCategory()))
                .content(request.getContent())
                .build();

        review.edit(reviewEditor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFound::new);

        finder.checkLegalWriterOfPost(review);

        reviewRepository.delete(review);
    }

    @Override
    public CustomPage<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = spec.getPageable(pageableDTO);
        Page<ReviewListResponse> page = reviewQueryRepository.search(cond, pageable);

        return CustomPage.<ReviewListResponse>builder()
                .page(page)
                .build();
    }

    @Override
    public List<ReviewListResponse> getReviewByMember(String loginId, Integer size, Long lastId) {
        finder.checkLegalLoginId(loginId);
        return reviewQueryRepository.getReviewByMember(loginId, size, lastId);

    }

    @Override
    public List<TagResponse> getRecommendationTags() {
        return reviewQueryRepository.getRecommendationTags().stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDetailResponse getReview(Long id) {
        return reviewQueryRepository.getReview(id);
    }

    @Override
    @Transactional
    public void viewCountUp(Long id) {
        reviewRepository.viewCountUp(id);
    }

    @Transactional
    public Tag findOrCreateTag(String tag) {
        return tagRepository.findByName(tag)
                .orElseGet(
                        () -> tagRepository.save(
                                Tag.builder().name(tag).build()
                        )
                );
    }

    private Set<ReviewTag> mapToReviewTags(Set<String> tags) {
        return tags.stream()
                .map(tag -> ReviewTag.createReviewTag(findOrCreateTag(tag)))
                .collect(Collectors.toSet());
    }
}

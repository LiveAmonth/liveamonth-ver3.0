package com.lam.liveamonthapp.domain.review.service;

import com.lam.liveamonthapp.domain.review.dto.condition.ReviewSearchCond;
import com.lam.liveamonthapp.domain.review.dto.reqeust.ReviewCreate;
import com.lam.liveamonthapp.domain.review.dto.reqeust.ReviewEdit;
import com.lam.liveamonthapp.domain.review.dto.response.ReviewDetailResponse;
import com.lam.liveamonthapp.domain.review.dto.response.ReviewListResponse;
import com.lam.liveamonthapp.domain.review.dto.response.TagResponse;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.paging.CustomPage;
import com.lam.liveamonthapp.paging.PageableDTO;

import java.util.List;

public interface ReviewService {

    PostIdResponse write(String loginId, ReviewCreate request);

    CustomPage<ReviewListResponse> search(ReviewSearchCond cond, PageableDTO pageableDTO);

    ReviewDetailResponse getReview(Long id);

    void viewCountUp(Long id);

    void edit(Long id, ReviewEdit reviewEdit);

    void delete(Long id);

    List<ReviewListResponse> getReviewByMember(String loginId, Integer size, Long lastId);

    List<TagResponse> getRecommendationTags();

    List<ReviewListResponse> getBestReview();

}

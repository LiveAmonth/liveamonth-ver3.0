package com.lam.liveamonthapp.domain.review.dto.condition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.lam.liveamonthapp.domain.review.constants.ReviewCategory;
import com.lam.liveamonthapp.domain.review.constants.ReviewSearchType;

import java.util.Set;

@Getter
@Setter
@Builder
public class ReviewSearchCond {

    private String searchWord;
    private Set<String> tags;
    private ReviewSearchType type;
    private ReviewCategory category;
}

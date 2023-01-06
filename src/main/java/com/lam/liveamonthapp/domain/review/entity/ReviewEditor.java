package com.lam.liveamonthapp.domain.review.entity;

import lombok.Builder;
import lombok.Getter;
import com.lam.liveamonthapp.domain.review.constants.ReviewCategory;

@Getter
public class ReviewEditor {

    private final String title;
    private final String content;
    private final ReviewCategory category;

    @Builder
    public ReviewEditor(String title, String content, ReviewCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}

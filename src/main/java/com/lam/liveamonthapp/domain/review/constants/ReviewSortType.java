package com.lam.liveamonthapp.domain.review.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.paging.metaModel.MetaModelType;

import static com.lam.liveamonthapp.domain.review.entity.QReview.review;
import static com.lam.liveamonthapp.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum ReviewSortType implements MetaModelType {
    ID_DESC("최신순", "id,desc", getColumn(review.id)),
    VIEW_DESC("조회순", "view,desc", getColumn(review.numberOfHits)),
    LIKE_DESC("인기순", "like,desc", getColumn(review.numberOfLikes)),
    COMMENTS_DESC("댓글순", "comments,desc", getColumn(review.numberOfComments));
    private final String value;
    private final String title;
    private final String metaData;

    @Override
    public String getCode() {
        return name();
    }
}

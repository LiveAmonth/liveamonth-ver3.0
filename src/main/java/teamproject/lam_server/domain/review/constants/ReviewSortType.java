package teamproject.lam_server.domain.review.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.paging.metaModel.MetaModelType;

import static teamproject.lam_server.domain.review.entity.QReview.review;
import static teamproject.lam_server.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum ReviewSortType implements MetaModelType {
    ID_DESC("최신순", "id,desc", getColumn(review.id)),
    COMMENTS_DESC("댓글많은순", "comments,desc", getColumn(review.numberOfComments)),
    LIKE_DESC("좋아요순", "like,desc", getColumn(review.numberOfLikes)),
    VIEW_DESC("조회순", "view,desc", getColumn(review.numberOfHits));
    private final String value;
    private final String title;
    private final String metaData;

    @Override
    public String getCode() {
        return name();
    }
}

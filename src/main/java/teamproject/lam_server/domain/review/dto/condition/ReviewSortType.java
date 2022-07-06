package teamproject.lam_server.domain.review.dto.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.paging.metaModel.MetaModelType;

import static teamproject.lam_server.domain.review.entity.QReview.review;
import static teamproject.lam_server.paging.metaModel.MetaModelUtil.getColumn;


@Getter
@AllArgsConstructor
public enum ReviewSortType implements MetaModelType {
    ID_ASC("오래된순", "id,asc", getColumn(review.id)),
    ID_DESC("최신순", "id,desc", getColumn(review.id)),
    VIEW_DESC("조회순", "view-count,desc", getColumn(review.viewCount));
    private String value;
    private String title;
    private String metaData;

    @Override
    public String getCode() {
        return title;
    }
}

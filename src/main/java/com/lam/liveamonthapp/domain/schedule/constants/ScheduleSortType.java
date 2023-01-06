package com.lam.liveamonthapp.domain.schedule.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.paging.metaModel.MetaModelType;

import static com.lam.liveamonthapp.domain.schedule.entity.QSchedule.schedule;
import static com.lam.liveamonthapp.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum ScheduleSortType implements MetaModelType {
    ID_DESC("최신순", "id,desc", getColumn(schedule.id)),
    VIEW_DESC("조회순", "view,desc", getColumn(schedule.numberOfHits)),
    LIKE_DESC("인기순", "like,desc", getColumn(schedule.numberOfLikes)),
    COMMENTS_DESC("댓글순", "comments,desc", getColumn(schedule.numberOfComments)),
    COST_DESC("비용순", "cost,desc", getColumn(schedule.totalCost));
    private final String value;
    private final String title;
    private final String metaData;

    @Override
    public String getCode() {
        return name();
    }
}

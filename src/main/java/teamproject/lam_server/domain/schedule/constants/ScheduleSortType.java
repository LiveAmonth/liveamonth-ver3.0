package teamproject.lam_server.domain.schedule.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.paging.metaModel.MetaModelType;

import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;
import static teamproject.lam_server.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum ScheduleSortType implements MetaModelType {
    ID_DESC("최신 순", "id,desc", getColumn(schedule.id)),
    VIEW_DESC("조회수 순", "view,desc", getColumn(schedule.numberOfHits)),
    LIKE_DESC("좋아요 순", "like,desc", getColumn(schedule.numberOfLikes)),
    COST_ASC("비용 적은 순", "cost,asc", getColumn(schedule.totalCost)),
    COST_DESC("비용 많은 순", "cost,desc", getColumn(schedule.totalCost)),
    ID_ASC("오래된 순", "id,asc", getColumn(schedule.id));
    private final String value;
    private final String title;
    private final String metaData;

    @Override
    public String getCode() {
        return name();
    }
}

package teamproject.lam_server.paging.sort;

import org.springframework.data.domain.Sort;
import teamproject.lam_server.paging.metaModel.MetaModelType;

public class SortStrategyImpl<T extends Enum<T> & MetaModelType> implements SortStrategy<T> {

    @Override
    public Sort.Order getSortOrder(T t, SortOption order) {
        return (order == SortOption.ASC) ? Sort.Order.asc(t.getMetaData()) : Sort.Order.desc(t.getMetaData());
    }
}

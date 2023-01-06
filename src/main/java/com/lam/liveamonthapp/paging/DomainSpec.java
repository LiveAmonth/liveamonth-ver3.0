package com.lam.liveamonthapp.paging;

import com.mysema.commons.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.lam.liveamonthapp.exception.badrequest.InvalidSortOption;
import com.lam.liveamonthapp.paging.sort.SortOption;
import com.lam.liveamonthapp.paging.sort.SortPair;
import com.lam.liveamonthapp.paging.sort.SortStrategy;
import com.lam.liveamonthapp.paging.sort.SortStrategyImpl;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class DomainSpec<T extends Enum<T>> {
    private final Class<T> clazz;
    @Getter
    @Setter
    private int defaultPage = 0;
    @Getter
    @Setter
    private int defaultSize = 10;
    private SortStrategy<T> sortStrategy;

    public DomainSpec(Class<T> clazz) {
        this.clazz = clazz;
        this.sortStrategy = new SortStrategyImpl();
    }

    public void changeSortStrategy(SortStrategy<T> sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public Pageable getPageable(PageableDTO dto) {
        Integer page = dto.getPage();
        Integer size = dto.getSize();
        return dto.getSorts().isEmpty() ?
                PageRequest.of(
                        page == null ? defaultPage : page,
                        size == null ? defaultSize : size
                ) :
                PageRequest.of(
                        page == null ? defaultPage : page,
                        size == null ? defaultSize : size,
                        Sort.by(getSortOrders(dto.getSorts()))
                );
    }

    private List<Sort.Order> getSortOrders(List<SortPair<String, SortOption>> sorts) {
        Assert.notNull(this.sortStrategy, "There is no sort strategy");

        List<Sort.Order> orders = new ArrayList<>();
        for (var o : sorts) {
            T column;
            try {
                column = Enum.valueOf(this.clazz, o.getColumn());
            } catch (IllegalArgumentException e) {
                throw new InvalidSortOption();
            }
            final Sort.Order order = sortStrategy.getSortOrder(column, o.getSortOption());
            Assert.notNull(order, "sort option error");

            orders.add(order);
        }
        return orders;
    }
}

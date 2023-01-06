package com.lam.liveamonthapp.paging;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomPage<T> {
    private List<T> content;
    private CustomPageable pageable;

    @Builder
    public CustomPage(Page<T> page) {
        this.content = page.getContent();
        this.pageable = CustomPageable.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();

    }
}

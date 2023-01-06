package com.lam.liveamonthapp.paging;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomPageable {

    private boolean first;
    private boolean last;
    private long totalElements;
    private int totalPages;

    @Builder
    public CustomPageable(boolean first, boolean last, long totalElements, int totalPages) {
        this.first = first;
        this.last = last;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}

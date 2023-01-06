package com.lam.liveamonthapp.paging;

import lombok.*;
import com.lam.liveamonthapp.paging.sort.SortOption;
import com.lam.liveamonthapp.paging.sort.SortPair;

import java.util.List;

@Getter
@Builder
@ToString
public class PageableDTO {

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 10;
    private List<SortPair<String, SortOption>> sorts;
}

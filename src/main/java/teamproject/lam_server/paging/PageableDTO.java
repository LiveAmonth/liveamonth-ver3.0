package teamproject.lam_server.paging;

import lombok.*;
import teamproject.lam_server.paging.sort.SortOption;
import teamproject.lam_server.paging.sort.SortPair;

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

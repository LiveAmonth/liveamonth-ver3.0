package teamproject.lam_server.paging;

import lombok.*;
import teamproject.lam_server.paging.sort.SortOption;
import teamproject.lam_server.paging.sort.SortPair;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class PageableDTO {
    private Integer page;
    private Integer size;
    private List<SortPair<String, SortOption>> sorts;
}

package teamproject.lam_server.paging;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import teamproject.lam_server.paging.sort.SortOption;
import teamproject.lam_server.paging.sort.SortPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageableArgumentResolver implements HandlerMethodArgumentResolver {
    private final String PAGE = "page";
    private final String SIZE = "size";
    private final String SORT = "sort";
    private final String SEPARATE = ",";
    private final String LATEST_CONTENT_ORDER = "id,desc";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageableDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        List<SortPair<String, SortOption>> sorts = new ArrayList<>();

        final var sortArr = webRequest.getParameterMap().get(SORT);
        if (sortArr != null) {
            for (var v : sortArr) {
                String[] keywords = v.split(SEPARATE);
                sorts.add(
                        SortPair.of(
                                (keywords[0] + "_" + keywords[1]).toUpperCase(),
                                SortOption.valueOf(keywords[1].toUpperCase())
                        ));
            }
            // 최신순 정렬이 아닌 경우 같은 결과값 정렬에 대해 최신순 정렬을 하기 위해 추가
            if (Arrays.stream(sortArr).noneMatch(s -> s.equals(LATEST_CONTENT_ORDER)))
                sorts.add(SortPair.of("ID_DESC", SortOption.DESC));
        }
        return PageableDTO.builder()
                .page(getValue(webRequest.getParameter(PAGE)))
                .size(getValue(webRequest.getParameter(SIZE)))
                .sorts(sorts)
                .build();
    }

    private Integer getValue(String param) {
        return param != null ? Integer.parseInt(param) : null;
    }
}

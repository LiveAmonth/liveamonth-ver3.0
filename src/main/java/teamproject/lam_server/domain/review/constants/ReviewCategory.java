package teamproject.lam_server.domain.review.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewCategory implements EnumMapperType {
    RECOMMEND_CITY("도시 추천"),
    RECOMMEND_FOOD("먹거리 추천"),
    RECOMMEND_VIEW("볼거리 추천"),
    FREE("자유게시판");
    private final String value;

    ReviewCategory(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}

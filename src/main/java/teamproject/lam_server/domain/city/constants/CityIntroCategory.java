package teamproject.lam_server.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CityIntroCategory implements EnumMapperType {
    INTRO("소개"),
    FOOD("먹거리"),
    VIEW("볼거리");
    private final String value;

    CityIntroCategory(String value) {
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

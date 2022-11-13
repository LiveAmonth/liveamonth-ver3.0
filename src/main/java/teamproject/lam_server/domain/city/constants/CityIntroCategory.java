package teamproject.lam_server.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CityIntroCategory implements EnumMapperType {
    INTRO("소개"),
    FOOD("먹거리"),
    VIEW("볼거리");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

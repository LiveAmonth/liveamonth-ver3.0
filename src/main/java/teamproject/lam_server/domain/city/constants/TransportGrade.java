package teamproject.lam_server.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TransportGrade implements EnumMapperType {
    GOOD("좋음"),
    FAIR("보통"),
    BAD("나쁨");
    private String value;

    @Override
    public String getCode() {
        return name();
    }

}

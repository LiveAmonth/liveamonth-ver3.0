package teamproject.lam_server.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TransportCategory implements EnumMapperType {
    T_SUBWAY("지하철역"),
    T_BUS("버스정류장"),
    T_BICYCLE("공공자전거"),
    T_BUS_TERMINAL("버스터미널"),
    T_TRAIN("기차역"),
    T_AIRPORT("공항");
    private String value;

    @Override
    public String getCode() {
        return name();
    }
}

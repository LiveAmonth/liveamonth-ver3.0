package teamproject.lam_server.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

import java.util.function.Function;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TransportCategory implements EnumMapperType {
    T_SUBWAY("지하철역", count -> count * 4),
    T_BUS("버스정류장", count -> count * 5),
    T_BICYCLE("공공자전거", count -> count * 6),
    T_BUS_TERMINAL("버스터미널", count -> count * 3),
    T_TRAIN("기차역", count -> count * 2),
    T_AIRPORT("공항", count -> count);
    private String value;
    private Function<Integer, Integer> policy;

    @Override
    public String getCode() {
        return name();
    }


    public int calcScore(int count) {
        return getPolicy().apply(count);
    }
}

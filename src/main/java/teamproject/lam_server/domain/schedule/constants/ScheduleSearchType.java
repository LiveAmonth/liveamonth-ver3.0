package teamproject.lam_server.domain.schedule.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScheduleSearchType implements EnumMapperType {
    MEMBER_NICKNAME("닉네임"),
    CITY_NAME("도시 이름"),
    START_DATE("시작 날짜");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

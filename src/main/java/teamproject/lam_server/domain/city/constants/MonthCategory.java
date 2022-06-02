package teamproject.lam_server.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MonthCategory implements EnumMapperType {
    JANUARY("1월"),
    FEBRUARY("2월"),
    MARCH("3월"),
    APRIL("4월"),
    MAY("5월"),
    JUNE("6월"),
    JULY("7월"),
    AUGUST("8월"),
    SEPTEMBER("9월"),
    OCTOBER("10월"),
    NOVEMBER("11월"),
    DECEMBER("12월");
    private String value;

    @Override
    public String getCode() {
        return name();
    }

}

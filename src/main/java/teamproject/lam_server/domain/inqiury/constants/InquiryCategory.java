package teamproject.lam_server.domain.inqiury.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
public enum InquiryCategory implements EnumMapperType {

    SCHEDULE("스케줄"),
    REVIEW("후기글"),
    CITY("도시 정보"),
    ETC("기타");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

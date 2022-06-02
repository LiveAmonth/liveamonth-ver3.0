package teamproject.lam_server.domain.customercenter.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerCenterMenu implements EnumMapperType {
    FAQ("FAQ"),
    PERSONAL_TERMS("개인정보 처리방침"),
    TERMS_AND_CONDITIONS("이용약관"),
    NOTICE("공지사항");

    private String value;

    @Override
    public String getCode() {
        return name();
    }
}

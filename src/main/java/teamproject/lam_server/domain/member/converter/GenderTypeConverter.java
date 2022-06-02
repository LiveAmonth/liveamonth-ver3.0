package teamproject.lam_server.domain.member.converter;

import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter extends CodeValueConverter<GenderType> {
    GenderTypeConverter() {
        super(GenderType.class);
    }
}

package teamproject.lam_server.app.user.converter;

import teamproject.lam_server.constants.CategoryConstants.GenderTypes;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter extends CodeValueConverter<GenderTypes> {
    GenderTypeConverter() {
        super(GenderTypes.class);
    }
}

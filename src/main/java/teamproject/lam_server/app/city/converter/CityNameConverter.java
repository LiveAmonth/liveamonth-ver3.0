package teamproject.lam_server.app.city.converter;

import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CityNameConverter extends CodeValueConverter<CityName> {
    CityNameConverter() {
        super(CityName.class);
    }
}

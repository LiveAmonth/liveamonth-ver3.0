package teamproject.lam_server.domain.city.converter;

import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

public class CityNameConverter extends CodeValueConverter<CityName> {
    CityNameConverter() {
        super(CityName.class);
    }
}

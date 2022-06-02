package teamproject.lam_server.domain.city.converter;

import teamproject.lam_server.domain.city.constants.CityInfoCategory;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

public class CityInfoCategoryConverter extends CodeValueConverter<CityInfoCategory> {
    CityInfoCategoryConverter() {
        super(CityInfoCategory.class);
    }
}

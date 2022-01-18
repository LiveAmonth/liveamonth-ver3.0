package teamproject.lam_server.app.city.converter;

import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CityInfoCategoryConverter extends CodeValueConverter<CityInfoCategory> {
    CityInfoCategoryConverter() {
        super(CityInfoCategory.class);
    }
}

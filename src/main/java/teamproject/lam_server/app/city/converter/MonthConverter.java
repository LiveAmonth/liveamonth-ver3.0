package teamproject.lam_server.app.city.converter;

import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_server.constants.CategoryConstants.Month;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class MonthConverter extends CodeValueConverter<Month> {
    MonthConverter() {
        super(Month.class);
    }
}

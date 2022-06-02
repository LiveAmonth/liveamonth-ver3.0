package teamproject.lam_server.domain.city.converter;

import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.global.converter.CodeValueConverter;

public class MonthCategoryConverter extends CodeValueConverter<MonthCategory> {
    MonthCategoryConverter() {
        super(MonthCategory.class);
    }
}

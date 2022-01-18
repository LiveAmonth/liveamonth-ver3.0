package teamproject.lam_server.app.city.converter;

import teamproject.lam_server.constants.CategoryConstants.TransportCategory;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransportCategoryConverter extends CodeValueConverter<TransportCategory> {
    TransportCategoryConverter() {
        super(TransportCategory.class);
    }
}

package teamproject.lam_server.domain.city.converter;

import teamproject.lam_server.domain.city.constants.TransportCategory;
import teamproject.lam_server.global.converter.CodeValueConverter;

import javax.persistence.Converter;

public class TransportCategoryConverter extends CodeValueConverter<TransportCategory> {
    TransportCategoryConverter() {
        super(TransportCategory.class);
    }
}

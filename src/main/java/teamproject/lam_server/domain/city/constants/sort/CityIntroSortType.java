package teamproject.lam_server.domain.city.constants.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.paging.metaModel.MetaModelType;

import static teamproject.lam_server.domain.city.entity.QCityIntro.cityIntro;
import static teamproject.lam_server.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum CityIntroSortType implements MetaModelType {
    CITY_NAME_ASC("도시 이름순(ㄱ-ㅎ)", "name,asc", getColumn(cityIntro.name)),
    CITY_NAME_DESC("도시 이름순(ㅎ-ㄱ)", "name,desc", getColumn(cityIntro.name));
    private final String value;
    private final String title;
    private final String metaData;

    @Override
    public String getCode() {
        return title;
    }
}

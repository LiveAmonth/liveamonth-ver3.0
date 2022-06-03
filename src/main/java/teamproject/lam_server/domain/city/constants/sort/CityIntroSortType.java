package teamproject.lam_server.domain.city.constants.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.paging.metaModel.MetaModelType;

import static teamproject.lam_server.domain.city.entity.QCityInfo.cityInfo;
import static teamproject.lam_server.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum CityIntroSortType implements MetaModelType {
    CITY_NAME_ASC("도시 이름순(ㄱ-ㅎ)", "name,asc", getColumn(cityInfo.name)),
    CITY_NAME_DESC("도시 이름순(ㅎ-ㄱ)", "name,desc", getColumn(cityInfo.name));
    private String value;
    private String title;
    private String metaData;

    @Override
    public String getCode() {
        return title;
    }
}

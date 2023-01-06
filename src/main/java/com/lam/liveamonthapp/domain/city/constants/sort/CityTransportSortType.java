package com.lam.liveamonthapp.domain.city.constants.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.paging.metaModel.MetaModelType;

import static com.lam.liveamonthapp.domain.city.entity.QCityTransport.cityTransport;
import static com.lam.liveamonthapp.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum CityTransportSortType implements MetaModelType {
    CITY_NAME_ASC("도시 이름순(ㄱ-ㅎ)", "name,asc", getColumn(cityTransport.name)),
    CITY_NAME_DESC("도시 이름순(ㅎ-ㄱ)", "name,desc", getColumn(cityTransport.name)),
    TRANSPORT_SCORE_ASC("교통점수 낮은순", "score,asc", getColumn(cityTransport.score)),
    TRANSPORT_SCORE_DESC("교통점수 높은순", "score,asc", getColumn(cityTransport.score));

    private final String value;
    private final String title;
    private final String metaData;

    @Override
    public String getCode() {
        return title;
    }
}

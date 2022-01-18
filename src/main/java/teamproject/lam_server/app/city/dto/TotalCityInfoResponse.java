package teamproject.lam_server.app.city.dto;

import com.querydsl.core.annotations.QueryProjection;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.entity.CityTransport;
import teamproject.lam_server.app.city.entity.CityWeather;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityName;

import java.util.ArrayList;
import java.util.List;

import static teamproject.lam_server.constants.CategoryConstants.*;

public class TotalCityInfoResponse {

    //== 도시 소개 정보 ==//
    private String name;
    private String content;
    private String image;

    //== 도시 교통 정보 ==//
    private List<CityTransport> transports = new ArrayList<>();

    //== 날씨 정보 ==//
    private List<CityWeather> weathers = new ArrayList<>();

    public TotalCityInfoResponse(CityInfo info, List<CityTransport> transports, List<CityWeather> weathers) {
        name = info.getName().getValue();
        content = info.getContent();
        image = info.getImage();

        this.transports = transports;

        this.weathers = weathers;


    }
}

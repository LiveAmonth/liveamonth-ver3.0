package com.lam.liveamonthapp.domain.city;

import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.constants.TransportCategory;
import com.lam.liveamonthapp.domain.city.dto.request.CityIntroCreate;
import com.lam.liveamonthapp.domain.city.dto.request.CityTransportCreate;
import com.lam.liveamonthapp.domain.city.dto.request.CityWeatherCreate;
import com.lam.liveamonthapp.domain.city.entity.CityIntro;
import com.lam.liveamonthapp.domain.city.entity.CityTransport;
import com.lam.liveamonthapp.domain.city.entity.CityWeather;
import com.lam.liveamonthapp.domain.city.repository.bulk.CityBatchRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityIntroRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityTransportRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityWeatherRepository;
import com.lam.liveamonthapp.util.JsonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CityRepositoryTest {
    @Autowired
    CityBatchRepository cityBatchRepository;
    @Autowired
    CityIntroRepository cityIntroRepository;
    @Autowired
    CityTransportRepository cityTransportRepository;
    @Autowired
    CityWeatherRepository cityWeatherRepository;

    List<CityIntroCreate> intros;
    List<CityTransportCreate> transports;
    List<CityWeatherCreate> weathers;

    @BeforeAll
    void setUp() {
        this.intros = new ArrayList<>(JsonUtil.jsonArrayToList("cityIntro", CityIntroCreate.class));
        this.transports = new ArrayList<>(JsonUtil.jsonArrayToList("cityTransport", CityTransportCreate.class));
        this.weathers = new ArrayList<>(JsonUtil.jsonArrayToList("cityWeather", CityWeatherCreate.class));

        cityBatchRepository.batchInsertIntro(this.intros);
        cityBatchRepository.batchInsertTransport(this.transports);
        cityBatchRepository.batchInsertWeather(this.weathers);
    }

    @Test
    @DisplayName("도시 이름으로 도시정보 조회")
    void find_city_intro_by_name() {
        // given
        CityName name = CityName.SE;
        List<CityIntroCreate> seoulIntros = this.intros.stream()
                .filter(intro -> intro.getName() == name)
                .collect(Collectors.toList());
        // when
        List<CityIntro> result = cityIntroRepository.findByName(name);
        List<String> contents = seoulIntros.stream()
                .map(CityIntroCreate::getContent)
                .collect(Collectors.toList());

        // then
        Assertions.assertThat(result.size()).isEqualTo(seoulIntros.size());
        for (CityIntro cityIntro : result) {
            Assertions.assertThat(contents).contains(cityIntro.getContent());
        }
    }

    @Test
    @DisplayName("도시 이름으로 교통정보 조회")
    void find_city_transports_by_name() {
        // given
        CityName name = CityName.SE;
        List<CityTransportCreate> seoulTransports = this.transports.stream()
                .filter(transport -> transport.getName() == name)
                .collect(Collectors.toList());
        // key: 교통 수단, value: 갯수
        Map<TransportCategory, Integer> stationMap = seoulTransports.stream()
                .collect(Collectors.toMap(
                        CityTransportCreate::getCategory,
                        CityTransportCreate::getStationCount
                ));

        // when
        List<CityTransport> result = cityTransportRepository.findByName(name);

        // then
        Assertions.assertThat(result.size()).isEqualTo(seoulTransports.size());
        for (CityTransport transport : result) {
            Assertions.assertThat(stationMap.containsKey(transport.getCityTransportCat())).isTrue();
            Assertions.assertThat(stationMap.values()).contains(transport.getScore());
        }
    }

    @Test
    @DisplayName("도시 이름으로 날씨정보 조회")
    void find_city_weathers_by_name() {
        // given
        CityName name = CityName.SE;

        List<CityWeatherCreate> seoulWeathers = this.weathers.stream()
                .filter(weather -> weather.getName() == name)
                .collect(Collectors.toList());
        // key: 월, value: 평균 온도
        Map<MonthCategory, Float> weatherMap = seoulWeathers.stream()
                .collect(Collectors.toMap(
                        CityWeatherCreate::getMonth,
                        CityWeatherCreate::getAvg
                ));
        // when
        List<CityWeather> result = cityWeatherRepository.findByName(name);
        Map<MonthCategory, Float> resultMap = result.stream()
                .collect(Collectors.toMap(
                        CityWeather::getMonth,
                        CityWeather::getAverageDegree));

        // then
        Assertions.assertThat(result.size()).isEqualTo(seoulWeathers.size());
        for (MonthCategory month : MonthCategory.values()) {
            Assertions.assertThat(resultMap.get(month)).isEqualTo(weatherMap.get(month));
        }
    }

}

package com.lam.liveamonthapp.performace.domain.city;

import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.dto.CityIntroDTO;
import com.lam.liveamonthapp.domain.city.dto.CityTransportDTO;
import com.lam.liveamonthapp.domain.city.dto.CityWeatherDTO;
import com.lam.liveamonthapp.domain.city.dto.response.CityGridDataResponse;
import com.lam.liveamonthapp.util.JsonUtil;
import com.lam.liveamonthapp.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * city entity refactoring -> city repository, entity 제거
 */
@ActiveProfiles("test")
@Slf4j
public class CityPerformanceTest {

//    @Autowired
//    CityBatchRepository cityBatchRepository;
//    @Autowired
//    CityIntroRepository cityIntroRepository;
//    @Autowired
//    CityTransportRepository cityTransportRepository;
//    @Autowired
//    CityWeatherRepository cityWeatherRepository;
//    @Autowired
//    CityQueryRepository cityQueryRepository;

    @Test
    @DisplayName("도시 이름으로 도시정보 조회 성능 비교")
    void find_city_intro_by_name() {
        // givenDD
        CityName name = CityName.SE;

        // when
//        TimeUtil.start();
//        List<CityIntro> dbResult = cityIntroRepository.findByName(name);
//        TimeUtil.end();
//        long dbTime = TimeUtil.takeTime("DB 데이터 조회");

        TimeUtil.start();
        // json 전체 데이터 가져오기
        List<CityIntroDTO> jsonData = new ArrayList<>(
                JsonUtil.jsonArrayToList("cityIntro", CityIntroDTO.class));
        // stream 이용해서 필터링하기
        List<CityIntroDTO> jsonResult = jsonData.stream()
                .filter(intro -> intro.getName() == name)
                .collect(Collectors.toList());
        TimeUtil.end();
        long jsonTime = TimeUtil.takeTime("JSON 데이터 조회");


        // then
//        assertThat(dbResult.size()).isEqualTo(jsonResult.size());
//        log.info("높은 성능={}, 성능 개선율={}",
//                dbTime < jsonTime ? "DB 조회" : "JSON 조회",
//                getPerformanceImprovementRate(dbTime, jsonTime));
    }

    @Test
    @DisplayName("도시 요약정보 조회 성능 비교")
    void get_city_summary_info() {
        // given
        // when
//        TimeUtil.start();
//        List<CityGridDataResponse> dbResult = cityQueryRepository.getCitySummaryInfo(MonthCategory.JAN);
//        TimeUtil.end();
//        long dbTime = TimeUtil.takeTime("DB 데이터 조회");

        TimeUtil.start();
        // json 전체 데이터 가져오기
        ArrayList<CityIntroDTO> jsonIntros =
                new ArrayList<>(JsonUtil.jsonArrayToList("cityIntro", CityIntroDTO.class));
        ArrayList<CityTransportDTO> jsonTransports =
                new ArrayList<>(JsonUtil.jsonArrayToList("cityTransport", CityTransportDTO.class));
        ArrayList<CityWeatherDTO> jsonWeathers =
                new ArrayList<>(JsonUtil.jsonArrayToList("cityWeather", CityWeatherDTO.class));

        // 도시 이미지, 이름 가져오기
        Map<CityName, String> filteredIntro = jsonIntros.stream()
                .filter(intro -> intro.getCategory() == CityIntroCategory.INTRO)
                .collect(Collectors.toMap(
                        CityIntroDTO::getName,
                        CityIntroDTO::getImage
                ));
        // 도시 평균 기온 가져오기
        Map<CityName, Float> filteredWeather = jsonWeathers.stream()
                .filter(weather -> weather.getMonth() == MonthCategory.JAN)
                .collect(Collectors.toMap(
                        CityWeatherDTO::getName,
                        CityWeatherDTO::getAvg
                ));
        // 도시 교통 점수 가져오기
        Map<CityName, Integer> filteredTransport =
                jsonTransports.stream()
                        .collect(Collectors.groupingBy(
                                CityTransportDTO::getName,
                                Collectors.summingInt(CityTransportDTO::getScore)));
        // CityGridResponse로 합치기
        List<CityGridDataResponse> jsonResult = Arrays.stream(CityName.values())
                .map(cityName -> new CityGridDataResponse(
                        cityName,
                        filteredIntro.get(cityName),
                        filteredWeather.get(cityName),
                        filteredTransport.get(cityName)))
                .collect(Collectors.toList());

        TimeUtil.end();
        long jsonTime = TimeUtil.takeTime("JSON 데이터 조회");


        // then
//        assertThat(dbResult.size()).isEqualTo(jsonResult.size());
//        log.info("높은 성능={}, 성능 개선율={}",
//                dbTime < jsonTime ? "DB 조회" : "JSON 조회",
//                getPerformanceImprovementRate(dbTime, jsonTime));
    }
}

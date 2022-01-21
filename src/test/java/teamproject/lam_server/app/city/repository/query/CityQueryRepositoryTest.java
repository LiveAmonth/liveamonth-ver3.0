package teamproject.lam_server.app.city.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import teamproject.lam_server.app.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.entity.CityTransport;
import teamproject.lam_server.app.city.entity.CityWeather;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_server.constants.CategoryConstants.CityName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory.INTRO;

@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
@Slf4j
class CityQueryRepositoryTest {

    @Autowired
    CityQueryRepository cityQueryRepository;


    @Test
    public void searchCityInfosTest() {
        CityInfoSearchCond cond = new CityInfoSearchCond();
        cond.setName(CityName.SEOUL);
        cond.setCategory(CityInfoCategory.FOOD);
        cond.setImageExtension("png");
        Pageable pageable = PageRequest.of(0, 5);
        Page<CityInfo> cityInfos = cityQueryRepository.searchCityInfos(cond, pageable);

        System.out.println("cityInfos.getTotalElements() = " + cityInfos.getTotalElements());
        System.out.println("cityInfos.getTotalPages() = " + cityInfos.getTotalPages());
    }
    @Test
    public void searchCityTransportsTest() {
        CityTransportSearchCond cond = new CityTransportSearchCond();
//        cond.setName(CityName.SEOUL);
        cond.setCategory(CategoryConstants.TransportCategory.T_BUS);
        cond.setCountLoe(300);
        cond.setCountGoe(160);
        Pageable pageable = PageRequest.of(0, 5);
        Page<CityTransport> cityTransports = cityQueryRepository.searchCityTransports(cond, pageable);

        System.out.println("cityTransports.getTotalElements() = " + cityTransports.getTotalElements());
        System.out.println("cityTransports.getTotalPages() = " + cityTransports.getTotalPages());
    }
    @Test
    public void searchCityWeatherTest() {
        CityWeatherSearchCond cond = new CityWeatherSearchCond();
        cond.setName(CityName.SEOUL);
//        cond.setMonth(CategoryConstants.Month.DECEMBER);
        cond.setDegreeGoe(10);
        cond.setDegreeLoe(20);
        Pageable pageable = PageRequest.of(0, 5);
        Page<CityWeather> cityWeathers = cityQueryRepository.searchCityWeather(cond, pageable);

        System.out.println("cityWeathers.getTotalElements() = " + cityWeathers.getTotalElements());
        System.out.println("cityWeathers.getTotalPages() = " + cityWeathers.getTotalPages());
    }
}
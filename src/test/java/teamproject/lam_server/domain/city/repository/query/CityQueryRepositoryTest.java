package teamproject.lam_server.domain.city.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import teamproject.lam_server.domain.city.constants.CityInfoCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.TransportCategory;
import teamproject.lam_server.domain.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.domain.city.entity.CityInfo;
import teamproject.lam_server.domain.city.entity.CityTransport;
import teamproject.lam_server.domain.city.entity.CityWeather;

@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
@Slf4j
class CityQueryRepositoryTest {

    @Autowired
    CityQueryRepository cityQueryRepository;


    @Test
    public void searchCityInfosTest() {
        CityInfoSearchCond cond = new CityInfoSearchCond();
        cond.setName(CityName.SE);
        cond.setCategory(CityInfoCategory.FOOD);
        Pageable pageable = PageRequest.of(0, 5);
        Page<CityInfo> cityInfos = cityQueryRepository.searchInfo(cond, pageable);

        System.out.println("cityInfos.getTotalElements() = " + cityInfos.getTotalElements());
        System.out.println("cityInfos.getTotalPages() = " + cityInfos.getTotalPages());
    }
    @Test
    public void searchCityTransportsTest() {
        CityTransportSearchCond cond = new CityTransportSearchCond();
//        cond.setName(CityName.SEOUL);
        cond.setCategory(TransportCategory.T_BUS);
        cond.setCountLoe(300);
        cond.setCountGoe(160);
        Pageable pageable = PageRequest.of(0, 5);
        Page<CityTransport> cityTransports = cityQueryRepository.searchTransport(cond, pageable);

        System.out.println("cityTransports.getTotalElements() = " + cityTransports.getTotalElements());
        System.out.println("cityTransports.getTotalPages() = " + cityTransports.getTotalPages());
    }
    @Test
    public void searchCityWeatherTest() {
        CityWeatherSearchCond cond = new CityWeatherSearchCond();
        cond.setName(CityName.SE);
//        cond.setMonth(CategoryConstants.Month.DECEMBER);
        cond.setDegreeGoe(10);
        cond.setDegreeLoe(20);
        Pageable pageable = PageRequest.of(0, 5);
        Page<CityWeather> cityWeathers = cityQueryRepository.searchWeather(cond, pageable);

        System.out.println("cityWeathers.getTotalElements() = " + cityWeathers.getTotalElements());
        System.out.println("cityWeathers.getTotalPages() = " + cityWeathers.getTotalPages());
    }
}

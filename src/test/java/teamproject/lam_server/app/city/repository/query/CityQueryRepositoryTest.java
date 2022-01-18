package teamproject.lam_server.app.city.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.dto.CityGridDataResponse;
import teamproject.lam_server.app.city.entity.City;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.constants.CategoryConstants;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory.INTRO;

@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
@Transactional
@Slf4j
class CityQueryRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    CityQueryRepository cityQueryRepository;

    @Autowired
    CityWeatherRepository cityWeatherRepository;

    public CityQueryRepositoryTest() {
    }

    private static <T extends City> void log(T t) {
        log.info("city info={}", t.toString());
    }


    @Test
    public void findCityInfo(){
        List<CityGridDataResponse> cityInfo = cityQueryRepository.findCityInfo(INTRO, CategoryConstants.Month.AUGUST);
        cityInfo.forEach(c -> log.info("result={}",c));
        assertThat(cityInfo.size()).isEqualTo(6);
    }

    @Test
    public void findCitySlide(){
        List<CityInfo> citySlideImage = cityQueryRepository.findCityIntroImage(INTRO);
        citySlideImage.forEach(CityQueryRepositoryTest::log);
    }
}
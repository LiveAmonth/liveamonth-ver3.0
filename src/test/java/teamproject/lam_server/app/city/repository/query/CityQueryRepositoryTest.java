package teamproject.lam_server.app.city.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import teamproject.lam_server.app.city.entity.CityInfo;
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
    @DisplayName("도시이름과 카테고리로 도시 정보 가져오기")
    public void findCityInfo_all_param() {
        //given
        CityName cityName = CityName.SEOUL;
        CityInfoCategory category = INTRO;

        //when
        List<CityInfo> cityInfos = cityQueryRepository.findCityInfo(cityName, category);

        //then
        cityInfos.forEach(cityInfo -> {
            assertThat(cityInfo.getCityInfoCat()).isEqualTo(category);
            assertThat(cityInfo.getName()).isEqualTo(cityName);
        });
    }

    @Test
    @DisplayName("도시이름으로 도시 정보 가져오기")
    public void findCityInfo_cityName() {
        //given
        CityName cityName = CityName.SEOUL;

        //when
        List<CityInfo> cityInfos_cityName = cityQueryRepository.findCityInfo(cityName, null);

        //then
        cityInfos_cityName.forEach(cityInfo -> {
            assertThat(cityInfo.getName()).isEqualTo(cityName);
        });
    }
    @Test
    @DisplayName("카테고리로 도시 정보 가져오기")
    public void findCityInfo_category() {
        //given
        CityInfoCategory category = INTRO;

        //when
        List<CityInfo> cityInfos = cityQueryRepository.findCityInfo(null, category);

        //then
        cityInfos.forEach(cityInfo -> {
            assertThat(cityInfo.getCityInfoCat()).isEqualTo(category);
        });
    }


}
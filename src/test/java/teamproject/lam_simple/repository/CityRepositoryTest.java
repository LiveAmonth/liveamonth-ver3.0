package teamproject.lam_simple.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_simple.domain.City;
import teamproject.lam_simple.domain.CityInfo;
import teamproject.lam_simple.domain.CityWeather;

import java.util.List;

import static org.junit.Assert.*;
import static teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory.*;
import static teamproject.lam_simple.constants.CategoryConstants.CityNames.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    @Transactional
    public void findCityWithGraphByName() throws Exception {

    }


}
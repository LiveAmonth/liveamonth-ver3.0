package teamproject.lam_simple.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityRepositoryTest {

    @Autowired
    private CityInfoRepository cityRepository;

    @Test
    @Transactional
    public void findCityWithGraphByName() throws Exception {

    }


}
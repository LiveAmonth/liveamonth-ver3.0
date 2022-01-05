package teamproject.lam_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.service.CityServiceImpl;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Transactional
class CityServiceTest {
    @Autowired
    CityServiceImpl cityService;
}
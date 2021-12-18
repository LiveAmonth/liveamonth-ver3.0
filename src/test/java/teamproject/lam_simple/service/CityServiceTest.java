package teamproject.lam_simple.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.domain.CityInfo;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory.INTRO;

@SpringBootTest
@Transactional
class CityServiceTest {
    @Autowired
    CityService cityService;
}
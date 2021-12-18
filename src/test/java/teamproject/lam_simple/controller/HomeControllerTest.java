package teamproject.lam_simple.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.CategoryConstants.CityNames;
import teamproject.lam_simple.domain.City;
import teamproject.lam_simple.domain.CityInfo;
import teamproject.lam_simple.service.CityService;

import java.util.Calendar;
import java.util.List;

import static teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory.FOOD;
import static teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory.INTRO;
import static teamproject.lam_simple.constants.CategoryConstants.CityNames.*;
import static teamproject.lam_simple.constants.CategoryConstants.Month;

@WebMvcTest(HomeController.class)
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Test
    @DisplayName("/home 테스트")
    public void homeTest() throws Exception{
        // given
        List<CityInfo> testData1 = cityService.findCityInfoByCategory(INTRO);
        Month testData2 = Month.values()[Calendar.getInstance().get(Calendar.MONTH)];

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("cityInfos",testData1))
                .andExpect(MockMvcResultMatchers.model().attribute("month",testData2));
    }

}
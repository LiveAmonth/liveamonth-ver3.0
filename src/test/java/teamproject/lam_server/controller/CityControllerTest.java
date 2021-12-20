package teamproject.lam_server.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.service.CityService;

import java.util.Map;

import static teamproject.lam_server.constants.CategoryConstants.CityNames.SE;

@WebMvcTest(CityController.class)
class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CityService cityService;

    @Test
    @DisplayName("/city 테스트")
    public void cityTest() throws Exception{
        // given
        CategoryConstants.CityNames menu = SE;
        CategoryConstants.CityNames[] cities = CategoryConstants.CityNames.values();
        Map<String, Object> cityInfos = cityService.getDataAboutCity(menu);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/city").param("menu",menu.name())
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("menu",menu))
                .andExpect(MockMvcResultMatchers.model().attribute("cities",cities))
                .andExpect(MockMvcResultMatchers.model().attribute("cityInfos",cityInfos));
    }

}
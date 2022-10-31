package teamproject.lam_server.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import teamproject.lam_server.init.service.InitCityService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("local")
//@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com") // (1)
class CityApiDocsTest {

    private MockMvc mockMvc;

    @Autowired
    private InitCityService initCityService;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .build();
        initCityService.initCityIntroData();
        initCityService.initCityTransportData();
        initCityService.initCityWeatherData();
    }

    @Test
    @DisplayName("도시 그리드 이미지 조회")
    void get_city_grid_infos() throws Exception {
        // expected
        ResultActions result = this.mockMvc.perform(get("/api/v1/city/grid-infos")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("city-grid-contents"));

    }
}

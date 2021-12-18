package teamproject.lam_simple.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static teamproject.lam_simple.constants.CategoryConstants.CustomerCenterCategory;

@WebMvcTest(CustomerCenterController.class)
class CustomerCenterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/customerCenter 테스트")
    public void customerCenterTest() throws Exception{
        // given
        CustomerCenterCategory menu = CustomerCenterCategory.faq;
        CustomerCenterCategory[] sideBarMenus = CustomerCenterCategory.values();

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/customerCenter").param("menu", menu.name())
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("menu", menu))
                .andExpect(MockMvcResultMatchers.model().attribute("sideBarMenus", sideBarMenus));
    }

}
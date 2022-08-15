package teamproject.lam_server.domain.schedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.domain.schedule.service.ScheduleService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.domain.schedule.constants.ScheduleSortType.ID_DESC;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("local")
@Slf4j
class ScheduleApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    private final String PREFIX_URL = "/api/v1/schedules";
    @Test
    @DisplayName("/search 조회시 스케줄을 조회한다.(최신 스케줄 순서 정렬)")
    void test_1() throws Exception {
        // given
        ScheduleSearchCond condition = new ScheduleSearchCond();
        condition.setCityName(null);
        condition.setMemberNickname(null);
        condition.setStartDate(null);

        String requestBody = objectMapper.writeValueAsString(condition);
        int totalElements = (int) scheduleRepository.count();
        int size = 4;
        int page = 0;
        String sort = ID_DESC.getCode();

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("size", String.valueOf(size));
        params.add("page", String.valueOf(page));
        params.add("sort", sort);

        // when
        List<Schedule> schedules = scheduleRepository.findAll().stream()
                .sorted(
                        Comparator.comparing(Schedule::getId, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());


        // then
        mockMvc.perform(
                        post(PREFIX_URL + "/search")
                                .params(params)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()", is(size)))
                .andExpect(jsonPath("$.data.totalElements", is(totalElements)))
                .andExpect(jsonPath("$.data.pageable.pageNumber", is(page)))
                .andExpect(jsonPath("$.data.content[0].id").value(schedules.get(0).getId()))
                .andDo(print());
    }

}

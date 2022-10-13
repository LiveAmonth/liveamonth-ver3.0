package teamproject.lam_server.domain.schedule.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("local")
@Slf4j
class ScheduleApiControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext ctx;
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    private ScheduleRepository scheduleRepository;
//    @Autowired
//    private ScheduleService scheduleService;
//
//    @BeforeEach
//    void setUp(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
//                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .alwaysDo(print())
//                .build();
//    }
//
//    private final String PREFIX_URL = "/api/v1/schedules";
//    @Test
//    @DisplayName("/search 조회시 스케줄을 조회한다.(최신 스케줄 순서 정렬)")
//    void test_1() throws Exception {
//        // given
//        ScheduleSearchCond condition = new ScheduleSearchCond();
//        condition.setCityName(null);
//        condition.setMemberNickname(null);
//        condition.setStartDate(null);
//
//        String requestBody = objectMapper.writeValueAsString(condition);
//        int totalElements = (int) scheduleRepository.count();
//        int size = 4;
//        int page = 0;
//        String sort = ID_DESC.getCode();
//
//        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("size", String.valueOf(size));
//        params.add("page", String.valueOf(page));
//        params.add("sort", sort);
//
//        // when
//        List<Schedule> schedules = scheduleRepository.findAll().stream()
//                .sorted(
//                        Comparator.comparing(Schedule::getId, Comparator.reverseOrder())
//                )
//                .collect(Collectors.toList());
//
//
//        // then
//        mockMvc.perform(
//                        post(PREFIX_URL + "/search")
//                                .params(params)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content.length()", is(size)))
//                .andExpect(jsonPath("$.data.totalElements", is(totalElements)))
//                .andExpect(jsonPath("$.data.pageable.pageNumber", is(page)))
//                .andExpect(jsonPath("$.data.content[0].id").value(schedules.get(0).getId()))
//                .andDo(print());
//    }

}

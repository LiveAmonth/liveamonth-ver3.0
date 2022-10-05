package teamproject.lam_server.init;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import teamproject.lam_server.init.service.*;

import javax.annotation.PostConstruct;


@Profile({"local", "test"})
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitCityService initCityService;
    private final InitMemberService initMemberService;
    private final InitScheduleService initScheduleService;
    private final InitCommentService initCommentService;
    private final InitInteractionService initInteractionService;
    private final InitInquiryService initInquiryService;

    @PostConstruct
    public void init() {
        initCityService.initCityIntroData();
        initCityService.initCityTransportData();
        initCityService.initCityWeatherData();
        initMemberService.initMemberData();
        initScheduleService.initScheduleData();
        initScheduleService.initScheduleContentData();
        initCommentService.initScheduleCommentData();
        initInteractionService.initInteractionData();
        initInquiryService.initInquiryData();
    }
}

package teamproject.lam_server.init;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Profile({"local","test"})
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitCityService initCityService;
    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initCityService.initCityIntroData();
        initCityService.initCityTransportData();
        initCityService.initCityWeatherData();
        initMemberService.initMemberData();
    }
}

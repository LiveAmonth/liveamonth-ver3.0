package teamproject.lam_server.domain.schedule.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.SimpleScheduleResponse;
import teamproject.lam_server.global.dto.OtherContentSearch;
import teamproject.lam_server.domain.schedule.service.ScheduleService;
import teamproject.lam_server.global.dto.ProfileCardResponse;
import teamproject.lam_server.global.dto.CustomResponse;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/schedules")
@Slf4j
public class ScheduleApiController {
    private final CustomResponse customResponse;

    private final ScheduleService scheduleService;
    private final MemberRepository memberRepository;


    @PostMapping("/other")
    public ResponseEntity<?> getOtherSchedules(@RequestBody @Valid OtherContentSearch search) {
        log.info("orderBy={}", search.getOrderBy());
        return customResponse.success(testData(search));
    }

    private List<SimpleScheduleResponse> testData(OtherContentSearch search){
        Member member = memberRepository.findByLoginId("rbdus7174").orElse(null);
        List<SimpleScheduleResponse> result = new ArrayList<>();
        for (int i = 1; i < search.getLimit()+1; i++) {
            SimpleScheduleResponse response = new SimpleScheduleResponse();
            response.setTitle(i+"번 스케줄 !!");
            response.setMember(new ProfileCardResponse(member));
            response.setViewCount(i*100);
            result.add(response);
        }
        return result;
    }
}

package teamproject.lam_server.domain.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleContentRequest;
import teamproject.lam_server.domain.schedule.dto.request.CreateScheduleRequest;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleDetailResponse;
import teamproject.lam_server.paging.PageableDTO;

public interface ScheduleService {

    void addSchedule(CreateScheduleRequest request);

    @Transactional
    void addScheduleContent(CreateScheduleContentRequest request);

    Page<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO);

    ScheduleDetailResponse getScheduleDetails(String nickname, String title);
}

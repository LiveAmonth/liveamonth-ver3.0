package teamproject.lam_server.domain.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.schedule.dto.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.paging.PageableDTO;

import java.util.List;

public interface ScheduleService {

    void addSchedule(ScheduleCreate request);

    @Transactional
    void addScheduleContent(ScheduleContentCreate request);

    Page<ScheduleCardResponse> search(ScheduleSearchCond cond, PageableDTO pageableDTO);

    List<ScheduleContentResponse> getScheduleContents(Long scheduleId);
}

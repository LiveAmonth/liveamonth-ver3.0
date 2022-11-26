package teamproject.lam_server.domain.schedule.service;

import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentEdit;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;

import java.util.List;

public interface ScheduleContentService {

    PostIdResponse addScheduleContent(Long scheduleId, ScheduleContentCreate request);

    void editScheduleContent(Long id, ScheduleContentEdit request);

    void deleteScheduleContent(Long id);

    List<ScheduleContentResponse> getScheduleContents(Long scheduleId);


}

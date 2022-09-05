package teamproject.lam_server.domain.schedule.service;

import teamproject.lam_server.domain.schedule.dto.editor.ScheduleContentEditor;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;

import java.util.List;

public interface ScheduleContentService {
    void addScheduleContent(Long scheduleId, ScheduleContentEditor request);

    void editScheduleContent(Long id, ScheduleContentEditor request);

    void deleteScheduleContent(Long id);

    List<ScheduleContentResponse> getScheduleContents(Long scheduleId);


}

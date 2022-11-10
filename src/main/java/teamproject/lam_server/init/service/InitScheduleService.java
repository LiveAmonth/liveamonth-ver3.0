package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.init.dto.InitScheduleContentRequest;
import teamproject.lam_server.util.JsonUtil;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitScheduleService {
    private static final String SCHEDULE = "schedule";
    private static final String SCHEDULE_CONTENT = "scheduleContent";
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    private final EntityManager em;

    @Transactional
    public void initScheduleData() {
        String query = "insert into schedule" +
                " (created_date, last_modified_date, created_by, last_modified_by, city_name, end_date, start_date, public_flag, title, number_of_hits, member_id) " +
                "VALUES (now(), now(), :created_by, :last_modified_by, :city_name, :end_date, :start_date, :public_flag, :title, 0, :member_id)";
        List<ScheduleCreate> requests = JsonUtil.jsonArrayToList(SCHEDULE, ScheduleCreate.class);
        long count = memberRepository.count();
        for (ScheduleCreate request : requests) {
            Member member = memberRepository.findAll().get((int) ((count - 1) * Math.random()));
            em.createNativeQuery(query)
                    .setParameter("created_by", member.getLoginId())
                    .setParameter("last_modified_by", member.getLoginId())
                    .setParameter("city_name", request.getCity())
                    .setParameter("end_date", request.getPeriod().getEndDate())
                    .setParameter("start_date", request.getPeriod().getStartDate())
                    .setParameter("public_flag", request.isPublicFlag())
                    .setParameter("title", request.getTitle())
                    .setParameter("member_id", member.getId())
                    .executeUpdate();
        }
    }

    @Transactional
    public void initScheduleContentData() {
        String query = "insert into schedule_content" +
                " (created_date, last_modified_date, created_by, last_modified_by, content, cost, end_date_time, start_date_time, title, schedule_id ) " +
                "VALUES (now(), now(), :created_by,:last_modified_by, :content, :cost, :end_date_time, :start_date_time, :title, :schedule_id)";
        List<InitScheduleContentRequest> requests = JsonUtil.jsonArrayToList(SCHEDULE_CONTENT, InitScheduleContentRequest.class);
        for (InitScheduleContentRequest request : requests) {
            Schedule schedule = scheduleRepository.findById(request.getScheduleId()).get();
            em.createNativeQuery(query)
                    .setParameter("created_by", schedule.getCreatedBy())
                    .setParameter("last_modified_by", schedule.getLastModifiedBy())
                    .setParameter("content", request.getContent())
                    .setParameter("cost", request.getCost())
                    .setParameter("end_date_time", request.getTimePeriod().getEndDateTime())
                    .setParameter("start_date_time", request.getTimePeriod().getStartDateTime())
                    .setParameter("title", request.getTitle())
                    .setParameter("schedule_id", schedule.getId())
                    .executeUpdate();
        }
    }
}

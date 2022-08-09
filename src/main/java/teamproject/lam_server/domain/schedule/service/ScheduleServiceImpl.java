package teamproject.lam_server.domain.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.CreateSchedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleContentRepository;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository scheduleContentRepository;
    private final MemberRepository memberRepository;

    @Override
    public void addSchedule(Long memberId, CreateSchedule request) {

    }
}

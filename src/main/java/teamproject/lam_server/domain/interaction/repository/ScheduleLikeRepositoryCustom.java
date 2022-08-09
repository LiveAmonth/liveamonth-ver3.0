package teamproject.lam_server.domain.interaction.repository;

import teamproject.lam_server.domain.interaction.entity.ScheduleLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public
interface ScheduleLikeRepositoryCustom {

    Optional<ScheduleLike> findById(Long from, Long id);

    Long getScheduleLikesCount(Long to);

    List<Member> getScheduleLikes(Long to);

    Long getLikedSchedulesCount(Long from);

    List<Schedule> getLikedSchedules(Long from);
}

package teamproject.lam_server.domain.schedule.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;
import teamproject.lam_server.paging.sort.SortOption;
import teamproject.lam_server.paging.sort.SortPair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static teamproject.lam_server.domain.schedule.constants.ScheduleSortType.*;


@SpringBootTest
@Transactional
@ActiveProfiles({"local", "oauth2"})
@Rollback
@Slf4j
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("스케줄 검색(최신, 오래된 순서 정렬)")
    void test_1() {
        // given
        DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

        ScheduleSearchCond condition = new ScheduleSearchCond();
        condition.setCityName(null);
        condition.setMemberNickname(null);
        condition.setStartDate(null);

        // when
        int size = (int) scheduleRepository.count();
        int page = 0;
        List<SortPair<String, SortOption>> sorts1 = Arrays.asList(SortPair.of(ID_DESC.name(), SortOption.DESC));
        List<SortPair<String, SortOption>> sorts2 = Arrays.asList(SortPair.of(ID_ASC.name(), SortOption.ASC));

        Pageable pageable1 = spec.getPageable(new PageableDTO(page, size, sorts1));
        Pageable pageable2 = spec.getPageable(new PageableDTO(page, size, sorts2));

        // 최신 순
        List<Schedule> searchSchedule1 = scheduleRepository.search(condition, pageable1).getContent();
        // 오래된 순
        List<Schedule> searchSchedule2 = scheduleRepository.search(condition, pageable2).getContent();

        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Schedule> scheduleSortByStream1 = allSchedules.stream()
                .sorted(
                        Comparator.comparing(Schedule::getId).reversed()
                )
                .collect(Collectors.toList());
        List<Schedule> scheduleSortByStream2 = allSchedules.stream()
                .sorted(
                        Comparator.comparing(Schedule::getId)
                )
                .collect(Collectors.toList());

        // then
        // 정렬 결과가 바르게 되엇는지 확인
        for (int i = 0; i < size - 1; i++) {
            assertThat(searchSchedule1.get(i).getId()).isGreaterThan(searchSchedule1.get(i + 1).getId());
            assertThat(searchSchedule2.get(i).getId()).isLessThan(searchSchedule2.get(i + 1).getId());
        }
        // 자바 스트림으로 정렬한 결과와 같은지 확인
        for (int i = 0; i < size; i++) {
            assertThat(searchSchedule1.get(i).getId()).isEqualTo(scheduleSortByStream1.get(i).getId());
            assertThat(searchSchedule2.get(i).getId()).isEqualTo(scheduleSortByStream2.get(i).getId());
        }
    }

    @Test
    @DisplayName("스케줄 검색(적은, 많은 비용 순서 정렬) / 같은 금액인 경우 최신 스케줄 우선 정렬")
    void test_2() {
        // given
        DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

        ScheduleSearchCond condition = new ScheduleSearchCond();
        condition.setCityName(null);
        condition.setMemberNickname(null);
        condition.setStartDate(null);

        // when
        int size = (int) scheduleRepository.count();
        int page = 0;
        List<SortPair<String, SortOption>> sorts1 = Arrays.asList(SortPair.of(COST_DESC.name(), SortOption.DESC), SortPair.of(ID_DESC.name(), SortOption.DESC));
        List<SortPair<String, SortOption>> sorts2 = Arrays.asList(SortPair.of(COST_ASC.name(), SortOption.ASC), SortPair.of(ID_DESC.name(), SortOption.DESC));

        Pageable pageable1 = spec.getPageable(new PageableDTO(page, size, sorts1));
        Pageable pageable2 = spec.getPageable(new PageableDTO(page, size, sorts2));

        // 비용 많은 순
        List<Schedule> descSearchSchedule = scheduleRepository.search(condition, pageable1).getContent();
        // 비용 적은 순
        List<Schedule> ascSearchSchedule = scheduleRepository.search(condition, pageable2).getContent();

        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Schedule> scheduleSortByStream1 = allSchedules.stream()
                .sorted(
                        Comparator.comparing(Schedule::getTotalCost, Comparator.reverseOrder())
                                .thenComparing(Schedule::getId, Comparator.reverseOrder())
                )

                .collect(Collectors.toList());
        List<Schedule> scheduleSortByStream2 = allSchedules.stream()
                .sorted(
                        Comparator.comparing(Schedule::getTotalCost)
                                .thenComparing(Schedule::getId, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());


        // then
        // 정렬 결과가 바르게 되엇는지 확인
        for (int i = 0; i < size - 1; i++) {
            Schedule prevDescSchedule = descSearchSchedule.get(i);
            Schedule nextDescSchedule = descSearchSchedule.get(i + 1);
            Schedule prevAscSchedule = ascSearchSchedule.get(i);
            Schedule nextAscSchedule = ascSearchSchedule.get(i + 1);

            // 앞, 뒤 순서의 비용이 같은 경우 앞의 스케줄의 Id 값이 더 커야한다.(최신순 정렬)
            if (prevDescSchedule.getTotalCost() != nextDescSchedule.getTotalCost()) {
                assertThat(prevDescSchedule.getTotalCost()).isGreaterThan(nextDescSchedule.getTotalCost());
            } else {
                assertThat(prevDescSchedule.getId()).isGreaterThan(nextDescSchedule.getId());
            }
            if (prevAscSchedule.getTotalCost() != nextAscSchedule.getTotalCost()) {
                assertThat(prevAscSchedule.getTotalCost()).isLessThan(nextAscSchedule.getTotalCost());
            } else {
                assertThat(prevAscSchedule.getId()).isGreaterThan(nextAscSchedule.getId());
            }

        }
        // 자바 스트림으로 정렬한 결과와 같은지 확인
        for (int i = 0; i < size; i++) {
            assertThat(descSearchSchedule.get(i).getTotalCost()).isEqualTo(scheduleSortByStream1.get(i).getTotalCost());
            assertThat(ascSearchSchedule.get(i).getTotalCost()).isEqualTo(scheduleSortByStream2.get(i).getTotalCost());
        }
    }

    @Test
    @DisplayName("스케줄 검색(좋아요 많은 순) / 좋아요 숫자가 같은 경우 최신 스케줄 우선 정렬")
    void test_3() {
        // given
        DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

        ScheduleSearchCond condition = new ScheduleSearchCond();
        condition.setCityName(null);
        condition.setMemberNickname(null);
        condition.setStartDate(null);

        // when
        int size = (int) scheduleRepository.count();
        int page = 0;
        List<SortPair<String, SortOption>> sorts1 = Arrays.asList(SortPair.of(LIKE_DESC.name(), SortOption.DESC), SortPair.of(ID_DESC.name(), SortOption.DESC));

        Pageable pageable1 = spec.getPageable(new PageableDTO(page, size, sorts1));

        // 비용 많은 순
        List<Schedule> searchSchedule = scheduleRepository.search(condition, pageable1).getContent();
        // 비용 적은 순

        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Schedule> scheduleSortByStream1 = allSchedules.stream()
                .sorted(
                        Comparator.comparing(Schedule::getNumberOfLikes, Comparator.reverseOrder())
                                .thenComparing(Schedule::getId, Comparator.reverseOrder())
                )

                .collect(Collectors.toList());


        // then
        // 정렬 결과가 바르게 되엇는지 확인
        for (int i = 0; i < size - 1; i++) {
            Schedule prevSchedule = searchSchedule.get(i);
            Schedule nextSchedule = searchSchedule.get(i + 1);

            if (prevSchedule.getNumberOfLikes() != nextSchedule.getNumberOfLikes()) {
                assertThat(prevSchedule.getNumberOfLikes()).isGreaterThan(nextSchedule.getNumberOfLikes());
            } else {
                assertThat(prevSchedule.getId()).isGreaterThan(nextSchedule.getId());
            }

        }
        // 자바 스트림으로 정렬한 결과와 같은지 확인
        for (int i = 0; i < size; i++) {
            assertThat(searchSchedule.get(i).getNumberOfLikes()).isEqualTo(scheduleSortByStream1.get(i).getNumberOfLikes());
        }
    }

    @Test
    @DisplayName("스케줄 검색(조회수 많은 순) / 조회수가 같은 경우 최신 스케줄 우선 정렬")
    void test_4() {
        // given
        DomainSpec<ScheduleSortType> spec = new DomainSpec<>(ScheduleSortType.class);

        ScheduleSearchCond condition = new ScheduleSearchCond();
        condition.setCityName(null);
        condition.setMemberNickname(null);
        condition.setStartDate(null);

        // when
        int size = (int) scheduleRepository.count();
        int page = 0;
        List<SortPair<String, SortOption>> sorts1 = Arrays.asList(SortPair.of(VIEW_DESC.name(), SortOption.DESC), SortPair.of(ID_DESC.name(), SortOption.DESC));

        Pageable pageable1 = spec.getPageable(new PageableDTO(page, size, sorts1));

        // 비용 많은 순
        List<Schedule> searchSchedule = scheduleRepository.search(condition, pageable1).getContent();
        // 비용 적은 순

        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Schedule> scheduleSortByStream1 = allSchedules.stream()
                .sorted(
                        Comparator.comparing(Schedule::getNumberOfHits, Comparator.reverseOrder())
                                .thenComparing(Schedule::getId, Comparator.reverseOrder())
                )

                .collect(Collectors.toList());


        // then
        // 정렬 결과가 바르게 되엇는지 확인
        for (int i = 0; i < size - 1; i++) {
            Schedule prevSchedule = searchSchedule.get(i);
            Schedule nextSchedule = searchSchedule.get(i + 1);

            if (prevSchedule.getNumberOfLikes() != nextSchedule.getNumberOfLikes()) {
                assertThat(prevSchedule.getNumberOfHits()).isGreaterThan(nextSchedule.getNumberOfHits());
            } else {
                assertThat(prevSchedule.getId()).isGreaterThan(nextSchedule.getId());
            }

        }
        // 자바 스트림으로 정렬한 결과와 같은지 확인
        for (int i = 0; i < size; i++) {
            assertThat(searchSchedule.get(i).getNumberOfHits()).isEqualTo(scheduleSortByStream1.get(i).getNumberOfHits());
        }
    }

}

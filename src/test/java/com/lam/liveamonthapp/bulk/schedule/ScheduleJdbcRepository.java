package com.lam.liveamonthapp.bulk.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleCreate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchScheduleInsert(List<ScheduleCreate> subItems, Long memberId) {
        this.jdbcTemplate.batchUpdate(
                "insert into schedule (created_date, last_modified_date, created_by, last_modified_by, city_name, number_of_hits, end_date, start_date, public_flag, title, member_id) values " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ScheduleCreate item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, "created");
                        ps.setString(4, "created");
                        ps.setString(5, item.getCity());
                        ps.setLong(6, 0L);
                        ps.setObject(7, item.getPeriod().getEndDate());
                        ps.setObject(8, item.getPeriod().getStartDate());
                        ps.setBoolean(9, item.isPublicFlag());
                        ps.setString(10, item.getTitle());
                        ps.setLong(11, memberId);
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }


}

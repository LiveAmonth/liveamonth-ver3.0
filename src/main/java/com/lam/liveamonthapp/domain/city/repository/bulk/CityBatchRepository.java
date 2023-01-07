package com.lam.liveamonthapp.domain.city.repository.bulk;

import com.lam.liveamonthapp.domain.city.dto.request.CityIntroCreate;
import com.lam.liveamonthapp.domain.city.dto.request.CityTransportCreate;
import com.lam.liveamonthapp.domain.city.dto.request.CityWeatherCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CityBatchRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertIntro(List<CityIntroCreate> subItems) {
        this.jdbcTemplate.batchUpdate(
                "insert into city_intro (created_date, last_modified_date, name, city_info_cat, content, image) values " +
                        "(?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CityIntroCreate item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, item.getName().getCode());
                        ps.setString(4, item.getCategory().getCode());
                        ps.setString(5, item.getContent());
                        ps.setString(6, item.getImage());
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }
    public void batchInsertTransport(List<CityTransportCreate> subItems) {
        this.jdbcTemplate.batchUpdate(
                "insert into city_transport (created_date, last_modified_date, name, city_transport_cat, score, station_count) values " +
                        "(?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CityTransportCreate item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, item.getName().getCode());
                        ps.setString(4, item.getCategory().getCode());
                        ps.setInt(5, item.getScore());
                        ps.setInt(6, item.getStationCount());
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }
    public void batchInsertWeather(List<CityWeatherCreate> subItems) {
        this.jdbcTemplate.batchUpdate(
                "insert into city_weather (created_date, last_modified_date, name, average_degree, max_degree, min_degree, month) values " +
                        "(?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CityWeatherCreate item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, item.getName().getCode());
                        ps.setFloat(4, item.getAvg());
                        ps.setFloat(5, item.getMax());
                        ps.setFloat(6, item.getMin());
                        ps.setString(7, item.getMonth().getCode());
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }
}

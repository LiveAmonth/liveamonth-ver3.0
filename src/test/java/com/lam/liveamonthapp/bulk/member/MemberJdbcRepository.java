package com.lam.liveamonthapp.bulk.member;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.lam.liveamonthapp.domain.member.constants.AccountState;
import com.lam.liveamonthapp.domain.member.constants.Role;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchInsert(List<MemberCreate> subItems) {
        this.jdbcTemplate.batchUpdate(
                "insert into member (created_date, last_modified_date, birth, email, gender, image, login_id, name, nickname, password, role, social_type, status) values " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        MemberCreate member = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setObject(3, member.getBirth());
                        ps.setString(4, member.getEmail());
                        ps.setString(5, member.getGender());
                        ps.setString(6, "default.img");
                        ps.setString(7, member.getLoginId());
                        ps.setString(8, member.getName());
                        ps.setString(9, member.getName());
                        ps.setString(10, member.getPassword());
                        ps.setString(11, Role.USER.name());
                        ps.setString(12, null);
                        ps.setString(13, AccountState.NORMAL.name());
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }
}

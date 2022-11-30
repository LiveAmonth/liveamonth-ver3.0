package teamproject.lam_server.jdbc.interaction;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InteractionJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchInsert(List<InteractionRequest> subItems) {
        this.jdbcTemplate.batchUpdate(
                "insert into follower (created_date, last_modified_date, to_member_id, from_member_id) values " +
                        "(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        InteractionRequest item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setLong(3, item.getTo());
                        ps.setLong(4, item.getFrom());
                    }
                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }
}

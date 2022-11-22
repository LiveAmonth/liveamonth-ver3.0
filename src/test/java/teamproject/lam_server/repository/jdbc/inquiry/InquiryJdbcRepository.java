package teamproject.lam_server.repository.jdbc.inquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.member.entity.Member;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InquiryJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchInsert(List<InquiryCreate> subItems, Member member) {
        this.jdbcTemplate.batchUpdate(
                "insert into inquiry (created_date, last_modified_date, created_by, last_modified_by, category, content, is_answered, title, inquiry_answer_id, member_id) values " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        InquiryCreate create = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, member.getLoginId());
                        ps.setString(4, member.getLoginId());
                        ps.setString(5, create.getCategory());
                        ps.setString(6, create.getContent());
                        ps.setBoolean(7, false);
                        ps.setString(8, create.getTitle());
                        ps.setObject(9, null);
                        ps.setLong(10, member.getId());
                    }
                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }
}

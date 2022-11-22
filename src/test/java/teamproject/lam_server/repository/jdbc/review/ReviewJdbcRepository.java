package teamproject.lam_server.repository.jdbc.review;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.review.dto.reqeust.ReviewCreate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchReviewInsert(List<ReviewCreate> subItems, Long memberId) {
        this.jdbcTemplate.batchUpdate(
                "insert into review (created_date, last_modified_date, created_by, last_modified_by, category, content, number_of_hits, title, member_id) values " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ReviewCreate item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, "created");
                        ps.setString(4, "created");
                        ps.setString(5, item.getCategory());
                        ps.setString(6, item.getContent());
                        ps.setLong(7, 0L);
                        ps.setString(8, item.getTitle());
                        ps.setLong(9, memberId);
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }

    public void batchTagInsert(List<String> subItems) {
        this.jdbcTemplate.batchUpdate(
                "insert into tag (created_date, last_modified_date, name) values " +
                        "(?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        String item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, item);
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }

    public void batchReviewTagInsert(List<Long> subItems, List<Long> tagIds) {
        this.jdbcTemplate.batchUpdate(
                "insert into review_tag (created_date, last_modified_date, review_id, tag_id) values " +
                        "(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Long reviewId = subItems.get(i);
                        Long tagId = tagIds.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setLong(3, reviewId);
                        ps.setLong(4, tagId);
                    }

                    public int getBatchSize() {
                        if (subItems.size() != tagIds.size()) throw new IllegalArgumentException();
                        return subItems.size();
                    }
                });
    }

}

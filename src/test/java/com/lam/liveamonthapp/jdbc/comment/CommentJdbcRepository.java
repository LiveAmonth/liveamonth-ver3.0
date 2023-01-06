package com.lam.liveamonthapp.jdbc.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.lam.liveamonthapp.domain.comment.dto.request.CommentCreate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void batchInsert(List<CommentCreate> subItems, Long memberId, Long reviewId) {
        this.jdbcTemplate.batchUpdate(
                "insert into review_comment (created_date, last_modified_date, created_by, last_modified_by, comment, member_id, parent_comment_id, review_id) values " +
                        "(?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CommentCreate item = subItems.get(i);
                        ps.setObject(1, LocalDateTime.now());
                        ps.setObject(2, LocalDateTime.now());
                        ps.setString(3, "created");
                        ps.setString(4, "created");
                        ps.setString(5, item.getComment());
                        ps.setLong(6, memberId);
                        ps.setObject(7, null);
                        ps.setLong(8, reviewId);
                    }

                    public int getBatchSize() {
                        return subItems.size();
                    }
                });
    }
}

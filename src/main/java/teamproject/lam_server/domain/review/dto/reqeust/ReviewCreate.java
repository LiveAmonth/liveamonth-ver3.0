package teamproject.lam_server.domain.review.dto.reqeust;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class ReviewCreate {

    private String title;
    private String content;
    private ReviewCategory category;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime reviewDateTime;
    private String writer;

    public Review toEntity(Member member) {
        return Review.builder()
                .title(title)
                .content(content)
                .reviewCategory(category)
                .member(member)
                .build();
    }
}

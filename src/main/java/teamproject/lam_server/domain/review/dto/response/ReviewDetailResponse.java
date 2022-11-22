package teamproject.lam_server.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.review.constants.ReviewCategory;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewDetailResponse {
    private Long id;
    private String title;
    private SimpleProfileResponse profile;
    private String content;
    private ReviewCategory category;
    private List<String> tags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd a h:mm", timezone = "Asia/Seoul")
    private LocalDateTime createDateTime;
    private long numberOfHits;
    private long numberOfComments;
    private long numberOfLikes;
}

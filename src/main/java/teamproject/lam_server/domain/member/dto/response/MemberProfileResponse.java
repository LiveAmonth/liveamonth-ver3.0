package teamproject.lam_server.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.constants.GenderType;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class MemberProfileResponse {
    private Long id;
    private String loginId;
    private String nickname;
    private String image;
    private String name;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birth;
    private GenderType gender;
    private long numberOfReviews;
    private long numberOfSchedules;
    private long numberOfFollowers;
    private long numberOfFollows;
}

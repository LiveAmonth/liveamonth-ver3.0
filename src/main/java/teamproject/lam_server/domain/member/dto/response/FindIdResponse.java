package teamproject.lam_server.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

import java.time.LocalDate;

import static teamproject.lam_server.util.StringUtil.coverContent;

@Getter
@Builder
public class FindIdResponse {
    private String loginId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate created;

    public static FindIdResponse of(Member member) {
        return FindIdResponse.builder()
                .loginId(coverContent(member.getLoginId()))
                .created(member.getCreatedDate().toLocalDate())
                .build();
    }

}


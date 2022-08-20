package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class CommentProfileResponse {

    private String nickname;
    private String image;

    public static CommentProfileResponse of(Member member){
        return CommentProfileResponse.builder()
                .nickname(member.getNickname())
                .image(member.getImage())
                .build();
    }
}

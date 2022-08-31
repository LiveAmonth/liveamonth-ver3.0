package teamproject.lam_server.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.interaction.constants.ReactType;
import teamproject.lam_server.domain.interaction.entity.ReactEntity;

@Getter
@Builder
public class ReactResponse {

    private boolean isReact;
    private ReactType reactType;

    public static ReactResponse of(ReactEntity react){
        return ReactResponse.builder()
                .isReact(false)
                .reactType(react.getType())
                .build();

    }
}

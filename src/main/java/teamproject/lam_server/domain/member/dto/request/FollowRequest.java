package teamproject.lam_server.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FollowRequest {

    @NotNull
    private Long from;

    @NotNull
    private Long to;
}

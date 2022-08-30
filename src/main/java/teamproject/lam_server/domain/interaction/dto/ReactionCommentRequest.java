package teamproject.lam_server.domain.interaction.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class ReactionCommentRequest {


    @NotNull
    private List<Long> ids;
}

package teamproject.lam_server.domain.interaction.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InteractionRequest {

    @NotNull
    private Long from;

    @NotNull
    private Long to;
}

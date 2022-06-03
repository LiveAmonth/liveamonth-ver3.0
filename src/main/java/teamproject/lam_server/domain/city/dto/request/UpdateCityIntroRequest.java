package teamproject.lam_server.domain.city.dto.request;

import lombok.Data;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateCityIntroRequest {
    @Lob
    @NotBlank
    private String content;
}

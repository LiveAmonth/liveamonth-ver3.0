package teamproject.lam_server.domain.city.dto.request;

import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityIntroEdit {
    @Lob
    @NotBlank
    private String content;
}

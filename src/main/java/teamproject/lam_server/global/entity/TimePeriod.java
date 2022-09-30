package teamproject.lam_server.global.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimePeriod {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Builder
    public TimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}

package teamproject.lam_server.global.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Embeddable
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TimePeriod {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDateTime;

}

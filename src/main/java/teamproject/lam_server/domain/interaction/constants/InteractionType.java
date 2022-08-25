package teamproject.lam_server.domain.interaction.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
public enum InteractionType implements EnumMapperType {

    MEMBER("member"),
    SCHEDULE("schedule"),
    SCHEDULE_COMMENT("scheduleComment"),
    REVIEW("review"),
    REVIEW_COMMENT("reviewComment");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

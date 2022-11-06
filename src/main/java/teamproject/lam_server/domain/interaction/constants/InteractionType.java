package teamproject.lam_server.domain.interaction.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
public enum InteractionType implements EnumMapperType {

    MEMBER("회원"),
    SCHEDULE("스케줄"),
    REVIEW("후기");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

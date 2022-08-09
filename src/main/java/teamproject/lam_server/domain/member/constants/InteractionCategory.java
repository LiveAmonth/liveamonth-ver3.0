package teamproject.lam_server.domain.member.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InteractionCategory implements EnumMapperType {
    FOLLOW("팔로우"),
    SCHEDULE("스케줄 좋아요"),
    REVIEW("후기 좋아요");

    private final String value;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}

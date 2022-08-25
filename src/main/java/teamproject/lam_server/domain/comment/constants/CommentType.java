package teamproject.lam_server.domain.comment.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
public enum CommentType implements EnumMapperType {
    SCHEDULE("schedule"),
    REVIEW("review");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

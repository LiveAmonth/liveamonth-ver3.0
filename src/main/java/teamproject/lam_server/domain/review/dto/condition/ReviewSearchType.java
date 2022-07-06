package teamproject.lam_server.domain.review.dto.condition;

import com.fasterxml.jackson.annotation.JsonFormat;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewSearchType implements EnumMapperType {
    TITLE("제목"),
    CONTENT("내용"),
    WRITER("닉네임");
    private String value;

    ReviewSearchType(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}

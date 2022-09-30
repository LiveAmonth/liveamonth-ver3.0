package teamproject.lam_server.domain.review.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewCategory implements EnumMapperType {
    SE_REVIEW("서울 후기"),
    GN_REVIEW("강릉 후기"),
    GJ_REVIEW("경주 후기"),
    BS_REVIEW("부산 후기"),
    YS_REVIEW("여수 후기"),
    JJ_REVIEW("제주 후기"),
    OTHER_REVIEW("다른 지역 후기"),
    QUESTION("질문 게시판"),
    FREE("자유 게시판");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

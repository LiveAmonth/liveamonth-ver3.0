package teamproject.lam_server.domain.review.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewCategory implements EnumMapperType {
    REVIEW_SE("서울 후기"),
    REVIEW_GN("강릉 후기"),
    REVIEW_GJ("경주 후기"),
    REVIEW_BS("부산 후기"),
    REVIEW_YS("여수 후기"),
    REVIEW_JJ("제주 후기"),
    REVIEW_OTHER("다른 지역 후기"),
    ETC_QUESTION("질문 게시판"),
    ETC_FREE("자유 게시판");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

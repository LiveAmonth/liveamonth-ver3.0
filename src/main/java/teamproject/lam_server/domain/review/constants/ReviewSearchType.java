package teamproject.lam_server.domain.review.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

import java.util.List;

import static teamproject.lam_server.domain.review.constants.ReviewCategory.*;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewSearchType implements EnumMapperType {
    REVIEW_LIVEAMONTH("리버먼스 지역", List.of(SE_REVIEW, GN_REVIEW, GJ_REVIEW, BS_REVIEW, YS_REVIEW, JJ_REVIEW)),
    REVIEW_OTHER("다른 지역", List.of(OTHER)),
    ETC_QUESTION("질문 게시판", List.of(QUESTION)),
    ETC_FREE("자유 게시판", List.of(FREE));

    private final String value;
    private final List<ReviewCategory> subs;

    @Override
    public String getCode() {
        return name();
    }
}

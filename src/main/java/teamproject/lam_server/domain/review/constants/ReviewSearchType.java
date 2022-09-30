package teamproject.lam_server.domain.review.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

import java.util.Arrays;
import java.util.List;

import static teamproject.lam_server.domain.review.constants.ReviewCategory.*;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewSearchType implements EnumMapperType {
    TOTAL("전체", List.of(ReviewCategory.values())),
    REVIEW("추천 & 후기", List.of(SE_REVIEW, GN_REVIEW, GJ_REVIEW, BS_REVIEW, YS_REVIEW, JJ_REVIEW, OTHER_REVIEW)),
    ETC("질문 & 자유", List.of(QUESTION, FREE));

    private final String value;
    private final List<ReviewCategory> subs;

    public static ReviewSearchType findByCategory(ReviewCategory category){
        return Arrays.stream(ReviewSearchType.values())
                .filter(group -> group.hasCategory(category))
                .findAny()
                .orElse(TOTAL);
    }

    @Override
    public String getCode() {
        return name();
    }

    public boolean hasCategory(ReviewCategory reviewCategory){
        return subs.stream().anyMatch(category -> category == reviewCategory);
    }

}

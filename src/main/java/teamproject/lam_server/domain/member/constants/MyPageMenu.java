package teamproject.lam_server.domain.member.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

import java.util.Set;

import static teamproject.lam_server.domain.member.constants.MyPageSubCategory.*;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MyPageMenu implements EnumMapperType {
    ACCOUNT("계정 관리", Set.of(MODIFY, DROP)),
    COMMUNITY("커뮤니티", Set.of(REVIEW, SCHEDULE)),
    INQUIRY("1:1 문의", Set.of(WRITE_INQUIRY, INQUIRY_ANSWER));
    private String value;
    private Set<MyPageSubCategory> subCategories;

    @Override
    public String getCode() {
        return name();
    }
}

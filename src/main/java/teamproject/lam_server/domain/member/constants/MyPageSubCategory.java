package teamproject.lam_server.domain.member.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MyPageSubCategory implements EnumMapperType {
    MODIFY("개인정보 변경"),
    DROP("회원 탈퇴"),
    REVIEW("내가 쓴 게시슬"),
    SCHEDULE("내가 쓴 스케줄"),
    WRITE_INQUIRY("1:1 문의하기"),
    INQUIRY_ANSWER("1:1 문의 답변");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}

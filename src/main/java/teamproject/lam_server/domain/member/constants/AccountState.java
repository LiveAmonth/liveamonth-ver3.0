package teamproject.lam_server.domain.member.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.global.enumMapper.EnumMapperType;


@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccountState implements EnumMapperType {
    NORMAL("일반"),
    DROP("탈퇴");
    private String value;

    @Override
    public String getCode() {
        return name();
    }
}

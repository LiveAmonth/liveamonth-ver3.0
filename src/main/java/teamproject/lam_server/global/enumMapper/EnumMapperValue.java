package teamproject.lam_server.global.enumMapper;

import lombok.Getter;
import lombok.ToString;

/**
 * Enum 을 바로 JSON으로 리턴하게 되면 name()만 출력됨.
 * 이를 해결하기 위해 DB의 컬럼값으로 사용하는 code와
 * view에서 사용될 desc를 같이 출력 (toString())
 * <p>
 * -> view layer에서 국제화를 통해 desc를 사용하지 않고
 * code 값만 사용한다 해도 해당 desc를 보고 해당 코드의
 * 내용이 무엇인지 파악가능
 */
@Getter
@ToString
public class EnumMapperValue {

    private final String code;
    private final String value;

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this.code = enumMapperType.getCode();
        this.value = enumMapperType.getValue();
    }
    public EnumMapperValue(String code, String value) {
        this.code = code;
        this.value = value;
    }

}

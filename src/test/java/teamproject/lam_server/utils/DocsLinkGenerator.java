package teamproject.lam_server.utils;

import teamproject.lam_server.global.enumMapper.EnumClassConst;

import static teamproject.lam_server.util.StringUtil.convertCamelToKebabCase;

public interface DocsLinkGenerator {
    static String generateLinkCode(EnumClassConst enumType) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", convertCamelToKebabCase(enumType.getClassName()), enumType.getValue(), "코드");
    }

    static String generateValue(EnumClassConst enumType) {
        return String.format("%s %s", enumType.getValue(), "코드명");
    }
}

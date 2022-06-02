package teamproject.lam_server.global.enumMapper;

public abstract class EnumUtil {

    public static EnumClassConst getEnumClassConst(String entityName, String path) {
        return EnumClassConst.valueOf(entityName.toUpperCase() + path);
    }
}

package teamproject.lam_server.util;

public abstract class StringUtil {
    private static String PREVIEW_DOT = "...";
    private static String COVER_STAR = "*";

    public static String previewContent(String content) {
        return content.substring(10) + PREVIEW_DOT;
    }

    public static String coverContent(String content) {
        return content.replaceAll("(?<=.{3}).", "*");
    }
}

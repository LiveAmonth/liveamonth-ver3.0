package teamproject.lam_server.util;

public abstract class StringUtil {
    private static final String PREVIEW_DOT = "...";
    private static final String COVER_STAR = "*";

    public static String previewContent(String content) {
        return content.substring(10) + PREVIEW_DOT;
    }

    public static String coverContent(String content) {
        return content.replaceAll("(?<=.{3}).", COVER_STAR);
    }

    public static String removeHtmlTag(String content) {
        return content.replaceAll("<(/)?([a-zA-Z0-9]*)(\\s[a-zA-Z0-9]*=[^>]*)?(\\s)*(/)?>", "");
    }
}

package teamproject.lam_server.util;

public abstract class SimpleResponseUtil {
    private static String previewDot = "...";

    public static String previewContent(String content) {
        return content.substring(10) + previewDot;
    }
}

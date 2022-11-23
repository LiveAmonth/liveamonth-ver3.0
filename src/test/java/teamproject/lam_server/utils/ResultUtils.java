package teamproject.lam_server.utils;

public abstract class ResultUtils {

    public static String getPerformanceImprovementRate(long a, long b) {
        return String.format("%.1f", (double) Math.abs(a - b) / Math.min(a, b)).concat("%");
    }
}

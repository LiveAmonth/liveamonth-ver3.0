package com.lam.liveamonthapp.utils;

public abstract class ResultUtils {

    public static String getPerformanceImprovementRate(long a, long b) {
        return String.format("%.1f", (double) Math.abs(a - b) / Math.min(a, b) * 100).concat("%");
    }
    public static String getPerformanceImprovementRate(double a, double b) {
        return String.format("%.1f", Math.abs(a - b) / Math.min(a, b) * 100).concat("%");
    }

    public static Double getPerformanceImprovementRateNumber(long a, long b) {
        return (double) Math.abs(a - b) / Math.min(a, b) * 100;
    }
}

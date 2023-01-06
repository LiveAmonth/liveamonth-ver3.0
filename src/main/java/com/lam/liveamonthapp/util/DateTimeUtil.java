package com.lam.liveamonthapp.util;

import java.time.LocalDateTime;

public abstract class DateTimeUtil {
    private static class TIME_MAXIMUM {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
        public static final String SEC_SUFFIX = "초 전";
        public static final String MIN_SUFFIX = "분 전";
        public static final String HOUR_SUFFIX = "시간 전";
        public static final String DAY_SUFFIX = "일 전";
        public static final String MONTH_SUFFIX = "달 전";
        public static final String YEAR_SUFFIX = "년 전";
    }

    public static String calcTimeBefore(LocalDateTime dateTime) {

        long curTime = System.currentTimeMillis();
        long regTime = java.sql.Timestamp.valueOf(dateTime).getTime();
        long diffTime = (curTime - regTime) / 1000;

        if (diffTime < TIME_MAXIMUM.SEC) {
            return diffTime + TIME_MAXIMUM.SEC_SUFFIX;
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            return diffTime + TIME_MAXIMUM.MIN_SUFFIX;
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            return (diffTime) + TIME_MAXIMUM.HOUR_SUFFIX;
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            return (diffTime) + TIME_MAXIMUM.DAY_SUFFIX;
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            return (diffTime) + TIME_MAXIMUM.MONTH_SUFFIX;
        } else {
            return (diffTime) + TIME_MAXIMUM.YEAR_SUFFIX;
        }
    }
}

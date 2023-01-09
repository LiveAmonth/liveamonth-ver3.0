package com.lam.liveamonthapp.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeUtil {
    private static long start;
    private static long end;

    public static void start() {
        start = System.currentTimeMillis();
    }

    public static void end() {
        end = System.currentTimeMillis();
    }

    public static long takeTime(String message) {
        log.info(message + " 속도={}ms", end - start);
        return end - start;
    }
}

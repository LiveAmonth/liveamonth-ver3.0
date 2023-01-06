package com.lam.liveamonthapp.util;

import java.util.ArrayList;
import java.util.List;

public abstract class StringUtil {
    private static final String PREVIEW_DOT = "...";
    private static final String COVER_STAR = "*";

    public static String previewContent(String content) {
        return content.substring(10) + PREVIEW_DOT;
    }

    public static String coverContent(String content) {
        return content.replaceAll("(?<=.{" + (content.length() - 3) + "}).", COVER_STAR);
    }

    public static String removeHtmlTag(String content) {
        return content.replaceAll("<(/)?([a-zA-Z0-9]*)(\\s[a-zA-Z0-9]*=[^>]*)?(\\s)*(/)?>", "");
    }

    public static String convertCamelToKebabCase(String content) {
        List<Integer> upperIdxes = new ArrayList<>();
        int changeCount = 0;
        for (int i = 1; i < content.length(); i++) {
            if (Character.isUpperCase(content.charAt(i))) {
                upperIdxes.add(i);
            }
        }
        for (Integer upperIdx : upperIdxes) {
            content = content.substring(0, upperIdx + changeCount) + '-' + content.substring(upperIdx + changeCount);
            changeCount++;
        }
        return content.toLowerCase();
    }
}

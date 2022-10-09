package teamproject.lam_server.util;

import java.text.DecimalFormat;

public abstract class NumberUtil {

    private static final String MILLION = "만";
    private static final String H_MILLION = "억";
    private static final DecimalFormat countFormat = new DecimalFormat("###,###.#");

    public static String countFormat(long number) {
        if (number < 1e4) return countFormat.format(number);
        if (number < 1e8) return countFormat.format(number / 1e4).concat(MILLION);
        else return countFormat.format(number / 1e8).concat(H_MILLION);
    }
}

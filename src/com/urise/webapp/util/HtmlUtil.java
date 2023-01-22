package com.urise.webapp.util;

import com.urise.webapp.model.Period;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Period period) {
        return DateUtil.format(period.getBegin()) + " - " + DateUtil.format(period.getEnd());
    }
}
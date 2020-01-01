package org.xiaogang.util;

public class StringUtil {
    public static String firstLower(String origin) {
        return origin.substring(0, 1).toLowerCase() + origin.substring(1);
    }

    public static String firstUpper(String origin) {
        return origin.substring(0, 1).toUpperCase() + origin.substring(1);
    }
}

package org.xiaogang.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class StringUtil {
    public static String firstLower(String origin) {
        return origin.substring(0, 1).toLowerCase() + origin.substring(1);
    }

    public static String firstUpper(String origin) {
        return origin.substring(0, 1).toUpperCase() + origin.substring(1);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}

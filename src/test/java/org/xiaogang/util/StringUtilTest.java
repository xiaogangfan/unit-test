package org.xiaogang.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilTest {


    @Test
    public void testRemoveGeneric() {
        String reg = "(.*?)(List<)(.*?)(>)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher("List<purchase>");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
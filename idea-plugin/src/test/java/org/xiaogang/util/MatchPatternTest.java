package org.xiaogang.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchPatternTest {
    
    @Test
    public void testMatchPattern(){
        String reg = "(.*?)(=)(.*?)(projectBidClearService\\.)(.*?)(\\()(.*?)(])";
        Pattern pattern = Pattern.compile(reg);
        CharSequence statement = "Optional[List<ProjectBidClear> projectBidClearList = projectBidClearService.list(Wrappers.lambdaQuery(ProjectBidClear.class)\n.eq(ProjectBidClear::getDeleted, Boolean.FALSE))]".replaceAll("\\n","");
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find()) {
            System.out.println("match--------------");;
        }
    }
}

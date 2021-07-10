package org.xiaogang.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchPatternTest {
    
    @Test
    public void testMatchPattern(){
        String reg = "(.*?)(=)(.*?)(taskMapper\\.)(.*?)(\\((\\n)?)(.*?)(])";
        Pattern pattern = Pattern.compile(reg);
        CharSequence statement = "Optional[{\n" +
                "            check(createDTO);\n" +
                "            FsscTaskDO taskDO = dto2DO(createDTO);\n" +
                "            taskMapper.insert(taskDO);\n" +
                "            return taskDO.getId();\n" +
                "        }]";
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find()) {
            System.out.println("match");;
        }
    }
}

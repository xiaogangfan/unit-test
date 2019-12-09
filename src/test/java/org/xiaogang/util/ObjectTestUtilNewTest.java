package org.xiaogang.util;

import org.junit.Test;
import test.domain.Exam;

import static org.junit.Assert.assertNotNull;

public class ObjectTestUtilNewTest {
    @Test
    public void testInitField() {
        Exam exam = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class);
        assertNotNull(exam.getCreatorName());
    }

    @Test
    public void testInitPro() {
        StringBuffer sb = new StringBuffer();
        ObjectTestUtilNew.initProcess(Exam.class, sb);
        System.out.println("initProcess:" + sb.toString());
        assertNotNull(sb.toString());
    }
}

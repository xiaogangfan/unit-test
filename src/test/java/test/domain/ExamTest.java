package test.domain;
import org.junit.Test;
import org.xiaogang.core.domain.model.JavaSourceFile;
import static org.junit.Assert.*;

import org.junit.Assert;
import unit.test.api.ObjectInit;
import org.junit.Before;
import java.util.Date;
 
public class ExamTest {
    Exam exam = null;

 
    @Test
    public void testNore() { 
        exam.nore();

        // Write the Assert code
        //Assert.assertTrue(true);
    }

 
    @Test
    public void testParamStr() { 
        // Initialize params of the method;
        String str = ObjectInit.random(String.class);
        Exam e = ObjectInit.random(Exam.class);
        Exam invokeResult = exam.paramStr(str, e);

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

 
    @Test
    public void testCanViewAnswer() { 
        Boolean invokeResult = exam.canViewAnswer();

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

 
    @Test
    public void testDigi() { 
        Integer invokeResult = exam.digi();

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

    @Before
    public void initInstance() {
        // Initialize the object to be tested
        exam = ObjectInit.random(Exam.class);
    }
}
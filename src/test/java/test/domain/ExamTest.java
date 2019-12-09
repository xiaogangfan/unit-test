package test.domain;

import org.junit.Before;
import org.junit.Test;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.util.ObjectTestUtilNew;

import java.util.Date;

public class ExamTest {
    Exam exam = null;


    @Before
    public void initInstance() {
        // Initialize the object to be tested; 
        Exam exam = new Exam();
        exam.setName("cfe44a8a");
        exam.setEffectTime(new Date());
        exam.setPeroid(1);
        exam.setEndTime(new Date());
        exam.setCreatorName("86517cc5");
        exam.setCreatorNo("9688b66a");
        exam.setStatus("95ee6f31");
        exam.setViewAnswer(7);
        JavaSourceFile javasourcefile = new JavaSourceFile();
        javasourcefile.setPathName("78783b0d");
        javasourcefile.setAbsDir("e33e5d04");
        javasourcefile.setPkg("5698de74");
        javasourcefile.setName("3715d9d6");
        javasourcefile.setClassName("eaa0d186");
        javasourcefile.setExtendsClass("9b425708");
        javasourcefile.setImplementInterface("5a86fdfe");
        exam.setJavaSourceFile(javasourcefile);
    }

    ;


    @Test
    public void testParamStr() {
        // Initialize params of the method; 
        String str = ObjectTestUtilNew.newObjectWithPropertiesValue(String.class);
        Exam e = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class);
        JavaSourceFile j = ObjectTestUtilNew.newObjectWithPropertiesValue(JavaSourceFile.class);

        exam.paramStr(str, e, j);

        //Write the Assert code

    }

    @Test
    public void testCanViewAnswer() {

        exam.canViewAnswer();

        //Write the Assert code

    }
}
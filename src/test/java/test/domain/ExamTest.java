package test.domain;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

import unit.test.api.ObjectInit;

import org.xiaogang.core.domain.model.JavaSourceFile;
import org.junit.Before;

public class ExamTest {
    Exam exam = null;


    @Before
    public void initInstance() {
        // Initialize the object to be tested; 
        Exam exam = new Exam();
        exam.setName("ffd9d103");
        exam.setEffectTime(new Date());
        exam.setPeroid(0);
        exam.setEndTime(new Date());
        exam.setCreatorName("9a7921fc");
        exam.setCreatorNo("85ec3eaa");
        exam.setStatus("d258e874");
        exam.setViewAnswer(9);
        JavaSourceFile javasourcefile = new JavaSourceFile();
        javasourcefile.setPathName("bd5f03b0");
        javasourcefile.setAbsDir("240c6b4a");
        javasourcefile.setPkg("ca806e71");
        javasourcefile.setName("06b21e44");
        javasourcefile.setClassName("444a52c9");
        javasourcefile.setExtendsClass("f6edbf77");
        javasourcefile.setImplementInterface("7e4aad53");
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
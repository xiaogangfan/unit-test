package test.domain;

import org.junit.Test;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.util.ObjectTestUtilNew;

public class ExamTest {

    @Test
    public void paramStrTest() {
        // Initialize the object to be tested; 
        Exam exam = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class);

        // Initialize params of the method; 
        String str = ObjectTestUtilNew.newObjectWithPropertiesValue(String.class);
        Exam e = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class);
        JavaSourceFile j = ObjectTestUtilNew.newObjectWithPropertiesValue(JavaSourceFile.class);

        exam.paramStr(str, e, j);

        //Write the Assert code

    }

    @Test
    public void canViewAnswerTest() {
        // Initialize the object to be tested; 
        Exam exam = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class);


        exam.canViewAnswer();

        //Write the Assert code

    }
}
package test.domain; 
import java.util.Date; 
 import org.xiaogang.util.ObjectTestUtilNew; 
import org.xiaogang.core.domain.model.JavaSourceFile; 
 public class ExamTest {
    public void paramStr(){ 
        //TODO write test code
        Exam exam = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class); 
        String str = ObjectTestUtilNew.newObjectWithPropertiesValue(String.class); 
        Exam e = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class); 
        JavaSourceFile j = ObjectTestUtilNew.newObjectWithPropertiesValue(JavaSourceFile.class); 
        exam.paramStr(str,e,j); 
        
    }
    public void canViewAnswer(){ 
        //TODO write test code
        Exam exam = ObjectTestUtilNew.newObjectWithPropertiesValue(Exam.class); 
        exam.canViewAnswer(); 
        
    }
}
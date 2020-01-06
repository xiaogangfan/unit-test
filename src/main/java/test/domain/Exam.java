package test.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.xiaogang.core.domain.model.JavaSourceFile;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-08-06 2:17 PM
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    private String name;
    private Date effectTime;
    private Integer peroid;
    private Date endTime;
    private String creatorName;
    private String creatorNo;

    private String status;
    private Integer viewAnswer;

    public Exam paramStr(String str, Exam e, JavaSourceFile j) {
        if (viewAnswer == null) {
            return null;
        }
        return this;
    }

    public boolean canViewAnswer() {
        if (viewAnswer == null) {
            return false;
        }
        return viewAnswer == 1;
    }

    public void nore() {

    }

    public Integer digi() {
        return 0;
    }
}
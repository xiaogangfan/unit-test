package test.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import java.util.Date;

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



    public boolean canViewAnswer() {
        if (viewAnswer == null) {
            return false;
        }
        return viewAnswer == 1;
    }
}
package org.xiaogang.core.domain.model;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 7:10 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Method {
    private String name;
    /**
     * 返回值类型
     */
    private String returnType;
    private Type type;
    private String body;

    private NodeList<Parameter> paramList;

}

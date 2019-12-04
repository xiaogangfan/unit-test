package org.xiaogang.core.domain.model;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
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
    private String returnType;
    private String body;
    private NodeList<Parameter> paramList;


}

package org.xiaogang.core.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.body.VariableDeclarator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.xiaogang.util.StringUtil;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 5:14 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JavaSourceFile {

    private String pathName;
    private String absDir;
    private String pkg;
    private String name;
    private String className;
    private String extendsClass;
    private String implementInterface;
    private Set<String> importList = new HashSet<>();
    private List<VariableDeclarator> fieldList;
    private List<Method> methodList;

    public String getVarName() {
        return StringUtil.firstLower(getName());
    }

}
package org.xiaogang.core.domain.model.sourcecodeparse.parse;

import com.github.javaparser.ast.body.MethodDeclaration;
import lombok.EqualsAndHashCode;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.util.StringUtil;

/**
 * 描述:
 * 针对已经生成Test文件的解析结果
 *
 * @author xiaogangfan
 * @create 2019-09-03 5:14 PM
 */
@EqualsAndHashCode(callSuper = false)
public class JavaTestCodeParser extends JavaCodeParser {

    public String getMethodString(MethodDeclaration methodDeclaration) {
        return "";
    }

    /**
     * 生成方法签名
     *
     * @param method
     * @return
     */
    @Override
    public String generateMethodSign(Method method) {
        //return StringUtil.firstLower(method.getName().substring(4)) +
        //    (CollectionUtils.isEmpty(method.getParamList()) ? "()" : "(" + method.getParamList().stream().map(
        //        t -> {
        //            return (t.getType().asString() + " " + t.getName().asString());
        //        }).collect(Collectors.toList()).stream().collect(
        //        Collectors.joining(",")) + "）")
        //    + ":" + method.getReturnType();
        if (method.getName().startsWith("test")) {
            return StringUtil.firstLower(method.getName().substring(4));
        } else {
            return method.getName();
        }

    }
}
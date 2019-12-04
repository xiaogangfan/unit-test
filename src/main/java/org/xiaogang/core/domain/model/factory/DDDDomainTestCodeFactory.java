package org.xiaogang.core.domain.model.factory;

import com.github.javaparser.ast.body.Parameter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.ModelEnum;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-09 6:01 PM
 */
public class DDDDomainTestCodeFactory extends AbstractTestCodeFactory {
    public DDDDomainTestCodeFactory(JavaSourceFile jsf) {
        this.jsf = jsf;
    }

    public DDDDomainTestCodeFactory() {
        super();
    }

    protected String invoke(ModelEnum ddd_model, Method method) {
        if (StringUtils.isNotBlank(jsf.getPkg())) {
//            importList.add("import " + jsf.getPkg() + "." + jsf.getName() + sep);

        }
        String methodParams = "";
        StringBuilder methodBody = new StringBuilder();


        // 实例化待测试的实例
        String instansVarName = jsf.getName().toLowerCase();
        methodBody.append(space8 + jsf.getName() + " " + instansVarName + " = ObjectTestUtilNew.newObjectWithPropertiesValue(" + jsf.getName() + ".class)" + sep);

        if (CollectionUtils.isNotEmpty(method.getParamList())) {
            for (int i = 0; i < method.getParamList().size(); i++) {
                Parameter param = method.getParamList().get(i);
                methodBody.append(space8 + param.getType() + " " + param.getName() + " = ObjectTestUtilNew.newObjectWithPropertiesValue(" + param.getType() + ".class)" + sep);
                methodParams += param.getName() + ",";
//                importList.add("import " + param.getType() + sep);
                importList.add("import org.xiaogang.util.ObjectTestUtilNew" + sep);
            }
        }
        methodParams = methodParams.length() > 0 ? methodParams.substring(0, methodParams.length() - 1) : "";

        methodBody.append(space8 + instansVarName + "." + method.getName() + "(" + methodParams + ")" + sep);
        return methodBody.toString();
    }

}
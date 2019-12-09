package org.xiaogang.core.domain.model.factory;

import com.github.javaparser.ast.body.Parameter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.ModelEnum;
import org.xiaogang.util.ObjectTestUtilNew;

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

    @Override
    protected String initInstance() {
        StringBuffer sb = new StringBuffer();

        importList.add("import org.junit.Before;");
        sb.append(enter);
        fieldList.add(space4 + jsf.getName() + " " + jsf.getName().substring(0, 1).toLowerCase() + jsf.getName().substring(1) + " = null;");
        sb.append(enter);
        sb.append(space4 + "@Before" + enter);
        sb.append(space4 + "public void initInstance(){" + enter);
        sb.append(space8 + "// Initialize the object to be tested" + sep);
        try {
            Class clz = Class.forName(jsf.getPkg() + "." + jsf.getName());
            ObjectTestUtilNew.initProcess(clz, sb);
        } catch (Exception e) {
            e.printStackTrace();
        }


        sb.append(space4 + "}" + sep);
        return sb.toString();
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
//        methodBody.append(space8 + "// Initialize the object to be tested" + sep);
//        methodBody.append(space8 + jsf.getName() + " " + instansVarName + " = ObjectTestUtilNew.newObjectWithPropertiesValue(" + jsf.getName() + ".class)" + sep + enter);

        if (CollectionUtils.isNotEmpty(method.getParamList())) {
            methodBody.append(space8 + "// Initialize params of the method" + sep);
            for (int i = 0; i < method.getParamList().size(); i++) {
                Parameter param = method.getParamList().get(i);
                methodBody.append(space8 + param.getType() + " " + param.getName() + " = ObjectTestUtilNew.newObjectWithPropertiesValue(" + param.getType() + ".class)" + sep);
                methodParams += param.getName() + ",";
//                importList.add("import " + param.getType() + sep);
                importList.add("import org.xiaogang.util.ObjectTestUtilNew" + sep);
            }
        }
        methodParams = methodParams.length() > 0 ? methodParams.substring(0, methodParams.length() - 1) : "";

        methodBody.append(enter + space8 + instansVarName + "." + method.getName() + "(" + methodParams + ")" + sep);
        return methodBody.toString();
    }

}
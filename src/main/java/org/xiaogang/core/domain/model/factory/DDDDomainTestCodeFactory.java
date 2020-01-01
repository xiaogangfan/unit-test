package org.xiaogang.core.domain.model.factory;

import com.github.javaparser.ast.body.Parameter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.ModelEnum;
import org.xiaogang.util.StringUtil;
import org.xiaogang.util.TestCodeUtil;

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
        fieldList.add(
            space4 + jsf.getName() + " " + jsf.getVarName()
                + " = null;");
        sb.append(enter + space4 + "@Before");
        sb.append(enter + space4 + "public void initInstance() {");
        sb.append(enter + space8 + "// Initialize the object to be tested");
        try {
            //Class clz = Class.forName(jsf.getPkg() + "." + jsf.getName());
            //ObjectTestUtilNew.initProcess(clz, sb);
            sb.append(enter + space8 + jsf.getVarName() + " = ObjectInit.random(" + jsf.getName() + ".class)" + sep);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sb.append(enter + space4 + "}");
        return sb.toString();
    }

    public DDDDomainTestCodeFactory() {
        super();
    }

    @Override
    protected String writeInvoke(ModelEnum ddd_model, Method method) {
        if (StringUtils.isNotBlank(jsf.getPkg())) {
        }
        String methodParams = "";
        StringBuilder methodBody = new StringBuilder();

        // 实例化待测试的实例
        String instansVarName = jsf.getName().toLowerCase();

        if (CollectionUtils.isNotEmpty(method.getParamList())) {
            methodBody.append(enter + space8 + "// Initialize params of the method" + sep);
            importList.add("import unit.test.api.ObjectInit" + sep);
            for (int i = 0; i < method.getParamList().size(); i++) {
                Parameter param = method.getParamList().get(i);
                methodBody.append(enter + space8 + param.getType() + " " + param.getName()
                    + " = ObjectInit.random(" + param.getType() + ".class)" + sep);
                if (method.getParamList().size() - 1 == i) {
                    methodParams += param.getName() + ",";
                } else {
                    methodParams += param.getName() + ", ";
                }
            }
        }
        methodParams = methodParams.length() > 0 ? methodParams.substring(0, methodParams.length() - 1) : "";
        if (!method.getType().isVoidType()) {
            String resultType = StringUtil.firstUpper(method.getType().asString());
            methodBody.append(
                enter + space8 + resultType + " invokeResult = " + instansVarName + "." + method.getName() + "("
                    + methodParams + ")" + sepAndenter);
        } else {
            methodBody.append(
                enter + space8 + instansVarName + "." + method.getName() + "("
                    + methodParams + ")" + sepAndenter);
        }

        return methodBody.toString();
    }

    @Override
    protected String writeAssert(ModelEnum ddd_model, Method method) {
        StringBuilder assertStr = new StringBuilder();
        if (method.getType().isVoidType()) {
            importList.add(TestCodeUtil.IMPO_ASSERT + sep);
            if (method.getParamList().size() == 0) {
                assertStr.append(enter + space8 + TestCodeUtil.ASSERTTRUE + sep);
            }
            for (Parameter parameter : method.getParamList()) {
                if (parameter.getType().isReferenceType()) {
                    assertStr.append(enter + space8 + TestCodeUtil.EQUALS + sep);
                }
            }

        } else {
            assertStr.append(enter + space8 + TestCodeUtil.EQUALS + sep);
            assertStr.append(enter + space8 + TestCodeUtil.RESULTASSERT + sep);
        }
        return assertStr.toString();
    }

}
package org.xiaogang.core.domain.model.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
public abstract class AbstractTestCodeFactory {
    public static final String sep = ";";
    public static final String enter = "\n";
    public static final String enter2 = "\n \n";
    public static final String sepAndenter = ";\n";
    public static final String impo = "import";
    public static final String tab = "  ";
    public static final String space4 = "    ";
    public static final String space8 = "        ";

    protected JavaSourceFile jsf = null;

    protected String pkg;
    protected Set<String> importList = new HashSet<>();
    protected Set<String> fieldList = new HashSet<>();
    protected String classHeader;
    protected String classBody;
    protected String implementInterface;
    protected String extendsClass;

    public AbstractTestCodeFactory() {
    }

    public static AbstractTestCodeFactory create(JavaSourceFile jsf) {
        AbstractTestCodeFactory factory = null;
        if (needMockCase(jsf)) {
            return new JmockitCodeFactory(jsf);
        }
        return new DDDDomainTestCodeFactory(jsf);
    }

    /**
     * @param jsf
     * @return
     */
    private static boolean needMockCase(JavaSourceFile jsf) {
        if (jsf.getName().endsWith("Service") || jsf.getName().endsWith("ServiceImpl") || jsf.getName().endsWith(
            "Application")) {
            return true;
        }
        return false;
    }

    public AbstractTestCodeFactory(JavaSourceFile jsf) {
        this.jsf = jsf;
    }

    public String createFileString() {
        setPkg();
        setImport();
        setClassHeader();
        setFields();
        setClassBody();

        return generateTestFileString();
    }

    protected void setFields() {
    }

    protected void setImport() {
        this.importList.add(impo + " static org.junit.Assert.*;");
        this.importList.add(impo + " org.junit.Test;");
        this.importList.add(enter);
        this.importList.addAll(jsf.getImportList());

    }

    protected void setPkg() {
        pkg = Optional.ofNullable(jsf.getPkg()).map(t -> "package " + t.replace(".main.", ".test.") + sep).orElse("");
    }

    protected void setClassHeader() {
        classHeader = "public class " + jsf.getName() + "Test ";
    }

    protected void setClassBody() {
        classBody = generateMethod();
    }

    public String generateTestFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pkg);
        if (CollectionUtils.isNotEmpty(importList)) {
            for (String s : importList) {
                sb.append(enter + s.trim());
            }
        }
        sb.append(enter2);
        sb.append(classHeader + "{");

        if (CollectionUtils.isNotEmpty(fieldList)) {
            for (String s : fieldList) {
                sb.append(enter + s);
            }
        }
        sb.append(classBody);
        sb.append(enter + "}");
        return sb.toString();
    }

    public List<String> generateImport() {
        return new ArrayList<>();
    }

    public String generateClassBody() {
        return generateMethod();
    }

    protected String generateMethod() {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isEmpty(jsf.getMethodList())) {
            return "";
        }
        sb.append(enter + initInstance());
        for (Method method : jsf.getMethodList()) {
            sb.append(enter2 + space4 + "@Test");
            sb.append(
                enter + space4 + "public void test" + StringUtil.firstUpper(method.getName()) + "() { ");
            sb.append(writeInvoke(method));
            sb.append(space8 + verify(ModelEnum.DDD_Model, method));
            sb.append(enter + space8 + "// Write the Assert code");
            sb.append(writeAssert(method));
            sb.append(enter + space4 + "}");
        }
        return sb.toString();
    }

    protected abstract String initInstance();

    protected String verify(ModelEnum ddd_model, Method method) {
        return "";
    }

    protected String writeInvoke(Method method) {
        if (StringUtils.isNotBlank(jsf.getPkg())) {
        }

        StringBuilder methodBody = new StringBuilder();

        String methodParams = "";
        // 实例化待测试的实例
        String instansVarName = StringUtil.firstLower(jsf.getName());

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

    protected String writeAssert(Method method) {
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
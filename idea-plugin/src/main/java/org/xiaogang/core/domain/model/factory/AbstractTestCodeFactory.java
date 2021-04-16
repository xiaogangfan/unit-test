package org.xiaogang.core.domain.model.factory;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.compress.utils.Lists;
//import org.apache.commons.lang.StringUtils;
import org.xiaogang.core.domain.model.Config;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.ModelEnum;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaSourceCodeParser;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaTestCodeParser;
import org.xiaogang.util.CollectionUtils;
import org.xiaogang.util.StringUtil;
import org.xiaogang.util.StringUtils;
import org.xiaogang.util.TestCodeUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-09 6:01 PM
 */
public abstract class AbstractTestCodeFactory {
    public static final String initInstance = "initInstance";
    public static final String sep = ";";
    public static final String enter = "\n";
    public static final String enter2 = "\n \n";
    public static final String sepAndenter = ";\n";
    public static final String impo = "import";
    public static final String tab = "  ";
    public static final String space4 = "    ";
    public static final String space8 = "        ";
    public static final String space12 = "            ";

    protected JavaSourceCodeParser javaSourceCodeParser = null;
    protected JavaTestCodeParser javaTestCodeParser = null;
    protected Config config = null;

    protected String pkg;
    protected Set<String> importSet = new HashSet<>();
    protected String classHeader;
    protected List<String> fieldSet = new ArrayList<>(16);
    protected List<String> methodSet = new ArrayList<>(16);

    public AbstractTestCodeFactory() {
    }

    public static AbstractTestCodeFactory create(JavaSourceCodeParser jsf,
                                                 JavaTestCodeParser javaTestCodeParser, Config config) {
        if (needMockCase(jsf)) {
            return new JmockitCodeFactory(jsf, javaTestCodeParser, config);
        }
        return new PoJoCodeFactory(jsf, javaTestCodeParser, config);
    }

    /**
     * @param jsf
     * @return
     */
    private static boolean needMockCase(JavaSourceCodeParser jsf) {
        if (jsf.getName().endsWith("Service") || jsf.getName().endsWith("ServiceImpl") || jsf.getName().endsWith(
                "Application")) {
            return true;
        }
        return false;
    }

    public AbstractTestCodeFactory(JavaSourceCodeParser javaSourceCodeParser) {
        this.javaSourceCodeParser = javaSourceCodeParser;
    }

    public String createFileString() {
        setPkg();
        setImport();
        setClassHeader();
        setFields();
        setMethods();

        return generateTestFileString();
    }

    protected void setFields() {
    }

    protected void setImport() {
        this.importSet.add(impo + " static org.junit.Assert.*;");
        this.importSet.add(impo + " org.junit.Test;");
        this.importSet.add(enter);
        this.importSet.addAll(javaSourceCodeParser.getImportList());

    }

    protected void setPkg() {
        pkg = Optional.ofNullable(javaSourceCodeParser.getPkg()).map(
                t -> "package " + t.replace(".main.", ".test.") + sep).orElse("");
    }

    protected void setClassHeader() {
        classHeader = "public class " + javaSourceCodeParser.getName() + "Test ";
    }

    protected void setMethods() {
        // Handle testfile
        handleTestFile();
        // Handle change method
        for (Method method : javaSourceCodeParser.getMethodList()) {
            if (needFilter(method)) {
                continue;
            }
            handleNewMethod(method);
        }
    }

    protected boolean needFilter(Method method) {
        String methodSign = javaSourceCodeParser.generateMethodSign(method);
        if (!config.getMethodNameList().contains(methodSign)) {
            return true;
        }
        if (javaSourceCodeParser.getMethodDeclarationMap() != null) {
            MethodDeclaration methodDeclaration = javaSourceCodeParser.getMethodDeclarationMap().get(methodSign);
            if (methodDeclaration != null) {
                return true;
            }
        }
        return false;
    }

    protected void handleNewMethod(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(writeMethodHeader(method));
//        sb.append(writeMethodMock(method));
        sb.append(writeMethodInvoke(method));
        sb.append(writeMethodAssert(method));
        sb.append(writeMethodFooter());
        methodSet.add(sb.toString());
    }

    protected void handleTestFile() {
        if (javaTestCodeParser != null && javaTestCodeParser.getMethodDeclarationMap() != null) {
            MethodDeclaration methodDeclaration = javaTestCodeParser.getMethodDeclarationMap().get(initInstance);
            if (methodDeclaration == null) {
//                methodSet.add(initInstance());
            }
            if (CollectionUtils.isNotEmpty(javaTestCodeParser.getMethodList())) {
                for (Method method : javaTestCodeParser.getMethodList()) {
                    String methodSign = javaTestCodeParser.generateMethodSign(method);
                    if (config.getMethodNameList().contains(methodSign)) {
                        continue;
                    }
                    methodSet.add(enter + space4 + generateTestSourceCodeMethodString(method.getMethodDeclaration()));
                }
            }
        } else {
//            methodSet.add(initInstance());
        }
    }

    protected String generateTestSourceCodeMethodString(MethodDeclaration methodDeclaration) {
        // todo
        return methodDeclaration.getTokenRange().get().toString();

    }

    public String generateTestFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pkg);
        if (CollectionUtils.isNotEmpty(importSet)) {
            for (String s : importSet) {
                sb.append(enter + s.trim());
            }
        }
        sb.append(enter2);
        sb.append(classHeader + "{");

        if (CollectionUtils.isNotEmpty(fieldSet)) {
            for (String s : fieldSet) {
                sb.append(enter + s);
            }
        }

        if (CollectionUtils.isNotEmpty(methodSet)) {
            for (String s : methodSet) {
                sb.append(enter + s);
            }
        }
        sb.append(enter + "}");
        return sb.toString();
    }

    public List<String> generateImport() {
        return new ArrayList<>();
    }

    protected String writeMethodMock(Method method) {
        return "";
    }

    protected String writeMethodFooter() {
        return enter + space4 + "}";
    }

    protected String writeMethodHeader(Method method) {
        StringBuffer methodHeader = new StringBuffer();
        methodHeader.append(enter2 + space4 + "@Test");
        methodHeader.append(
                enter + space4 + "public void test" + StringUtil.firstUpper(method.getName()) + "() { ");
        return methodHeader.toString();
    }

    protected String initInstance() {
        return "";
    }

    protected String verify(ModelEnum ddd_model, Method method) {
        return "";
    }

    protected String writeMethodInvoke(Method method) {
        if (StringUtils.isNotBlank(javaSourceCodeParser.getPkg())) {
        }


        StringBuilder methodBody = new StringBuilder();

        String methodParams = "";
        // 实例化待测试的实例
        String instansVarName = StringUtil.firstLower(javaSourceCodeParser.getName());
        importSet.add("import unit.test.api.ObjectInit;");
        importSet.add("import org.junit.Assert;");


        // Initialize the object to be tested
        methodBody.append(enter + space8 + "// Initialize the object to be tested");
        methodBody.append(
                enter + space8 + javaSourceCodeParser.getName() + " " + javaSourceCodeParser.getVarName()
                        + " = ObjectInit.random(" + javaSourceCodeParser.getName() + ".class)" + sep);


        if (CollectionUtils.isNotEmpty(method.getParamList())) {
            methodBody.append(enter + space8 + "// Initialize params of the method");
            importSet.add("import unit.test.api.ObjectInit" + sep);
            for (int i = 0; i < method.getParamList().size(); i++) {
                Parameter param = method.getParamList().get(i);
                // 初始化参数
                if (isList(param.getType().toString())) {
                    methodBody.append(enter + space8 + param.getType() + " " + param.getName()
                            + " = ObjectInit.randomList(" + removeGeneric(param.getType().toString()) + ".class,1)" + sep);
                } else {
                    methodBody.append(enter + space8 + param.getType() + " " + param.getName()
                            + " = ObjectInit.random(" + removeGeneric(param.getType().toString()) + ".class)" + sep);
                }

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
            if (method.getMethodDeclaration().isPrivate()) {
                importSet.add("import mockit.Deencapsulation;");
                methodBody.append(
                        enter + space8 + resultType + " invokeResult = (" + resultType + ")" + "Deencapsulation.invoke("
                                + instansVarName + ", \"" + method.getName()
                                + (StringUtils.isNotBlank(methodParams) ? (", " + methodParams) : methodParams) + ")"
                                + sepAndenter);
            } else {
                methodBody.append(
                        enter + space8 + resultType + " invokeResult = " + instansVarName + "." + method.getName() + "("
                                + methodParams + ")" + sepAndenter);
            }

        } else {
            if (method.getMethodDeclaration().isPrivate()) {
                importSet.add("import mockit.Deencapsulation;");
                methodBody.append(
                        enter + space8 + "Deencapsulation.invoke("
                                + instansVarName + ", " + method.getName() + ", "
                                + (StringUtils.isNotBlank(methodParams) ? (", " + methodParams) : methodParams) + ")"
                                + sepAndenter);
            } else {
                methodBody.append(
                        enter + space8 + instansVarName + "." + method.getName() + "("
                                + methodParams + ")" + sepAndenter);
            }

        }
        return methodBody.toString();
    }

    private boolean isList(String type) {
        String reg = "(.*?)(List<)(.*?)(>)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(type);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    public static String removeGeneric(String type) {
        String reg = "(.*?)(List<)(.*?)(>)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(type.toString());
        while (matcher.find()) {
            return matcher.group(3);
        }
        return type;
    }

    public static void main(String[] args) {
//        String s = AbstractTestCodeFactory.removeGeneric("List<Purchase>");
        System.out.println("xxx");
    }

    protected String writeMethodAssert(Method method) {

        StringBuilder assertStr = new StringBuilder();
        assertStr.append(enter + space8 + "// Write the Assert code");
        if (method.getType().isVoidType()) {
            importSet.add(TestCodeUtil.IMPO_ASSERT + sep);
            assertStr.append(enter + space8 + TestCodeUtil.ASSERTTRUE + sep);
            for (Parameter parameter : method.getParamList()) {
                if (parameter.getType().isReferenceType()) {
                    assertStr.append(enter + space8 + TestCodeUtil.EQUALS + sep);
                }
            }

        } else {
//            assertStr.append(enter + space8 + TestCodeUtil.EQUALS + sep);
            assertStr.append(enter + space8 + TestCodeUtil.RESULTASSERT + sep);
        }
        return assertStr.toString();
    }
}
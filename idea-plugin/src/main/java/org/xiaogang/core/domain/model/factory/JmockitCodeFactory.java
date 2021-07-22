package org.xiaogang.core.domain.model.factory;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.Statement;
import org.xiaogang.core.domain.model.Config;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaSourceCodeParser;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaTestCodeParser;
import org.xiaogang.util.CollectionUtils;
import org.xiaogang.util.StringUtil;
import org.xiaogang.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.compress.utils.Lists;
//import org.apache.commons.lang3.StringUtils;
//import lombok.NoArgsConstructor;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-09 6:01 PM
 */
public class JmockitCodeFactory extends AbstractTestCodeFactory {

    public JmockitCodeFactory(JavaSourceCodeParser JavaSourceCodeParser, JavaTestCodeParser javaTestCodeParser,
                              Config config) {
        this.javaSourceCodeParser = JavaSourceCodeParser;
        this.javaTestCodeParser = javaTestCodeParser;
        this.config = config;
    }

    @Override
    protected void handleTestFile() {
        if (javaTestCodeParser != null && javaTestCodeParser.getMethodDeclarationMap() != null) {
            if (CollectionUtils.isNotEmpty(javaTestCodeParser.getMethodList())) {
                for (Method method : javaTestCodeParser.getMethodList()) {
                    String methodSign = javaTestCodeParser.generateMethodSign(method);
                    if (config.getMethodNameList().contains(methodSign)) {
                        continue;
                    }
                    methodSet.add(enter + space4 + generateTestSourceCodeMethodString(method.getMethodDeclaration()));
                }
            }
        }
    }

    public JmockitCodeFactory() {
        super();
    }

    @Override
    protected void setFields() {
        if (CollectionUtils.isEmpty(javaSourceCodeParser.getFieldList())) {
            return;
        }

        fieldSet.add(
                space4 + "@Tested" + enter + space4 + javaSourceCodeParser.getName() + " " + javaSourceCodeParser.getVarName()
                        + ";");
        importSet.add("import mockit.Tested;");

        for (VariableDeclarator var : javaSourceCodeParser.getFieldList()) {
//            if (var.getParentNode().get().getTokenRange().toString().contains("@")) {
            if (isSimple(var)) {
                fieldSet.add(enter + space4 + var.getType().asString() + " " + StringUtil
                        .firstLower(var.getName().asString())
                        + " = " + "ObjectInit.random(" + getSimpleClass(var) + ") ;");
                continue;
            }

            fieldSet.add(
                    space4 + "@Injectable" + enter + space4 + var.getType().asString() + " " + StringUtil
                            .firstLower(var.getName().asString())
                            + ";");
            importSet.add("import mockit.Injectable;");
            importSet.add("import org.junit.runner.RunWith;");
//            }
        }

    }

    private String getSimpleClass(VariableDeclarator var) {
        String type = var.getType().toString();
        if ("String".equals(type)) {
            return "String.class";
        }
        if ("boolean".equals(type) || "Boolean".equals(type)) {
            return "Boolean.class";
        }
        if ("char".equals(type) || "Character".equals(type)) {
            return "Character.class";
        }
        if ("int".equals(type) || "Integer".equals(type)) {
            return "Character.class";

        }
        if ("byte".equals(type) || "Byte".equals(type)) {
            return "Byte.class";

        }
        if ("short".equals(type) || "Short".equals(type)) {
            return "Short.class";
        }
        if ("long".equals(type) || "Long".equals(type)) {
            return "Long.class";
        }
        if ("float".equals(type) || "Float".equals(type)) {
            return "Float.class";
        }
        if ("double".equals(type) || "Double".equals(type)) {
            return "Double.class";
        }
        return "";
    }

    private boolean isSimple(VariableDeclarator var) {
        String type = var.getType().toString();
        if ("String".equals(type)) {
            return true;
        }
        if ("boolean".equals(type) || "Boolean".equals(type)) {
            return true;
        }
        if ("char".equals(type) || "Character".equals(type)) {
            return true;
        }
        if ("int".equals(type) || "Integer".equals(type)) {
            return true;
        }
        if ("byte".equals(type) || "Byte".equals(type)) {
            return true;
        }
        if ("short".equals(type) || "Short".equals(type)) {
            return true;
        }
        if ("long".equals(type) || "Long".equals(type)) {
            return true;
        }
        if ("float".equals(type) || "Float".equals(type)) {
            return true;
        }
        if ("double".equals(type) || "Double".equals(type)) {
            return true;
        }
        return false;
    }

    @Override
    protected void setClassHeader() {
//        importSet.add("import mockit.integration.junit4.JMockit;");
//        classHeader = "@RunWith(JMockit.class)" + enter;
        classHeader = "public class " + javaSourceCodeParser.getName() + "Test ";
    }

    @Override
    protected String writeMethodInvoke(Method method) {
        // TODO 改成支持private方法的动态调用
        //return super.writeMethodInvoke(method);
        StringBuilder methodBody = new StringBuilder();

        String methodParams = "";
        // 实例化待测试的实例
        String instansVarName = StringUtil.firstLower(javaSourceCodeParser.getName());

        if (CollectionUtils.isNotEmpty(method.getParamList())) {
            methodBody.append(enter + space8 + "// Initialize params of the method");
            importSet.add("import unit.test.api.ObjectInit" + sep);
            for (int i = 0; i < method.getParamList().size(); i++) {
                Parameter param = method.getParamList().get(i);
//                methodBody.append(enter + space8 + param.getType() + " " + param.getName()
////                methodBody.append(enter + space8  + param.getName()
//                        + " = ObjectInit.random(" + param.getType() + ".class)" + sep);

                methodBody.append(enter + space8 + (writeMethodInvoke_param(param) + sep));


                if (method.getParamList().size() - 1 == i) {
                    methodParams += param.getName();
                } else {
                    methodParams += param.getName() + ", ";
                }
            }
        }

        methodBody.append(writeMethodMock(method));

        if (!method.getType().isVoidType()) {
            String resultType = StringUtil.firstUpper(method.getType().asString());
            if (method.getMethodDeclaration().isPrivate()) {
//                importSet.add("import mockit.Deencapsulation;");

                methodBody.append(writePrivateInvoke(instansVarName, methodParams, method));
//                methodBody.append(enter + space8 +"Method "+ instansVarName+method.getName()+" = " +instansVarName+".getClass().getMethod("+method.getName()+");");
//                methodBody.append(enter + space8 +instansVarName+method.getName()+".setAccessible(true)");
//                methodBody.append(enter + space8 +instansVarName+method.getName()+".invoke("+(StringUtils.isNotBlank(methodParams) ? (", " + methodParams) : methodParams) + ")"+")"+ sepAndenter);
            } else {
                methodBody.append(
                        enter + space8 + resultType + " invokeResult = " + instansVarName + "." + method.getName() + "("
                                + methodParams + ")" + sepAndenter);
            }

        } else {
            if (method.getMethodDeclaration().isPrivate()) {
//                importSet.add("import mockit.Deencapsulation;");
                methodBody.append(writePrivateInvoke(instansVarName, methodParams, method));
            } else {
                methodBody.append(
                        enter + space8 + instansVarName + "." + method.getName() + "("
                                + methodParams + ")" + sepAndenter);
            }

        }
        return methodBody.toString();
    }


//    private String writePrivateInvoke(String instansVarName, String methodParams, Method method) {
//        importSet.add("import java.lang.reflect.Method;");
//        StringBuffer methodBodyPrivate = new StringBuffer();
//        methodBodyPrivate.append(enter + space8 + "try { );");
//        methodBodyPrivate.append(enter + space8 + space8 + "Method " + instansVarName + method.getName() + " = " + instansVarName + ".getClass().getMethod(\"" + method.getName() + "\");");
//        methodBodyPrivate.append(enter + space8 + space8 + instansVarName + method.getName() + ".setAccessible(true);");
//        methodBodyPrivate.append(enter + space8 + space8 + instansVarName + method.getName() + ".invoke(" + (StringUtils.isNotBlank(methodParams) ? methodParams : "") + ")" + ");");
//        methodBodyPrivate.append(enter + space8 + " } catch (Exception e) {");
//        methodBodyPrivate.append(enter + space8 + space8 + "e.printStackTrace();");
//        methodBodyPrivate.append(enter + space8 + "}" + sepAndenter);
//        return methodBodyPrivate.toString();
//    }


    @Override
    protected String writeMethodAssert(Method method) {
        String superResult = super.writeMethodAssert(method);
        StringBuffer verify = new StringBuffer();
//        verify.append(enter + space8 + "new Verifications() {{");
//        verify.append(enter + space8 + "}};");
        return verify.toString() + superResult;
    }

    @Override
    protected String writeMethodMock(Method method) {
        StringBuffer mock = new StringBuffer();
        importSet.add("import mockit.Expectations;");
        importSet.add("import unit.test.api.ObjectInit;");
        importSet.add("import org.junit.Assert;");
        mock.append(enter + space8 + "new Expectations() {{");
        List<String> matcherList = serviceInvokeMatcher();
        Iterator<Statement> iterator = method.getMethodDeclaration().getBody().get().getStatements().iterator();
        boolean hasMock = false;

        while (iterator.hasNext()) {
            Node entry = iterator.next();
//            String statment = "";
//            List<List<MockStatement>> collect = entry.getChildNodes().stream().map(row -> {
//                ArrayList<String> lineContentList = Lists.newArrayList(row.getTokenRange().toString().split("\n"));
//                List<MockStatement> invokeList = lineContentList.stream().map(lineContent -> {
//                    MockStatement matchStatment = getMatchStatment(lineContent, matcherList);
//                    return matchStatment;
//                }).collect(Collectors.toList());
//                return invokeList;
//            }).collect(Collectors.toList());
            for (Node childNode : entry.getChildNodes()) {

                MockStatement matchStatment = getMatchStatment(childNode.getTokenRange().toString(), matcherList);
                if (matchStatment == null || StringUtils.isBlank(matchStatment.getInvokeStatment())) {
                    continue;
                }
                mock.append(enter + space12 + "//" + StringUtil.replaceBlank(matchStatment.getInvokeStatment()) + sep);
                mock.append(
                        enter + space12 + "//result = ObjectInit.random(" + matchStatment.getResultType() + ".class)"
                                + sep);
//                    statment = "";
                //} else {
                //    statment += entry.getTokenRange().toString();
                //}
                hasMock = true;


                //if (childNode.getTokenRange().toString().trim().endsWith(";")) {
                //使用正则将关键的调用抓取出来
//                ArrayList<String> lineContentList = Lists.newArrayList(childNode.getTokenRange().toString().split(";"));
//                for (String content : lineContentList) {
//                    content = content.replaceAll("\\n","");
//                    MockStatement matchStatment = getMatchStatment(content, matcherList);
//                    if (matchStatment == null || StringUtils.isBlank(matchStatment.getInvokeStatment())) {
//                        continue;
//                    }
//                    mock.append(enter + space12 + "//" + StringUtil.replaceBlank(matchStatment.getInvokeStatment()) + sep);
//                    mock.append(
//                            enter + space12 + "//result = ObjectInit.random(" + matchStatment.getResultType() + ".class)"
//                                    + sep);
////                    statment = "";
//                    //} else {
//                    //    statment += entry.getTokenRange().toString();
//                    //}
//                    hasMock = true;
//                }
            }
        }
        if (!hasMock) {
            return "";
        }


        mock.append(enter + space8 + "}};");
        return mock.toString();
    }

    public MockStatement getMatchStatment(String statement, List<String> matcherList) {
        for (String reg : matcherList) {
            if (StringUtils.isBlank(reg.trim())) {
                continue;
            }
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(statement);
            while (matcher.find()) {
                return wrapMockStatement(matcher.group());
            }
        }
        return null;

    }

    private MockStatement wrapMockStatement(String group) {
        MockStatement mockStatement = new MockStatement();
        String statment = group.substring(9, group.length() - 1);
        String[] statmentArray = statment.trim().split("=");
        mockStatement.setInvokeStatment(statmentArray[1]);
        String[] declari = statmentArray[0].trim().split(" ");
        List<String> collect = Arrays.stream(declari).filter(row -> StringUtils.isNotBlank(row.trim())).collect(
                Collectors.toList());
        if (collect.size() > 1) {
            for (int i = 0; i < collect.size(); i++) {
                if (collect.size() - 1 == i) {
                    mockStatement.setResultVar(collect.get(1));
                } else {
                    mockStatement.setResultType(mockStatement.getResultType() + collect.get(i));
                }
            }

        } else {
            mockStatement.setResultVar(collect.get(0));
        }
        return mockStatement;
    }


    public static class MockStatement {
        public MockStatement() {
        }

        public String getResultType() {
            return resultType;
        }

        public void setResultType(String resultType) {
            this.resultType = resultType;
        }

        public String getResultVar() {
            return resultVar;
        }

        public void setResultVar(String resultVar) {
            this.resultVar = resultVar;
        }

        public String getInvokeStatment() {
            return invokeStatment;
        }

        public void setInvokeStatment(String invokeStatment) {
            this.invokeStatment = invokeStatment;
        }

        private String resultType = "";
        private String resultVar;
        private String invokeStatment;
    }

    private List<String> serviceInvokeMatcher() {
        List<String> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(javaSourceCodeParser.getFieldList())) {
            return new ArrayList<>();
        }
        for (VariableDeclarator var : javaSourceCodeParser.getFieldList()) {
//            if (var.getParentNode().get().getTokenRange().toString().contains("@")) {
            result.add("(.*?)(=)(.*?)(" + var.getName().toString() + "\\.)(.*?)(\\((\\n)?)(.*?)(])");
//            }
        }
        return result;
    }

    public static void main(String[] args) {
        //String target = " sdfsd settleResultForShowMapper.findForShow(conditions);sdf";
        String target = " Optional[SimpleResultDTO<Map<String, SimpleBatchInfoDTO>> mapSimpleResultDTO = "
                + "inspectHsfService.searchBatch(\n"
                + "            batchIdList)]";
        String reg = "(inspectHsfService\\.)(.*?)(\\((\\n)?)(.*?)(\\))";
        //String reg = "(settleResultForShowMapper\\.)(.*?)(\\))";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(target);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        //// 查找的字符串
        //String line = "（乙方）:xxx科技股份有限公司     （乙方）:xxx有限公司     （乙方）:xxx技术股份有限公司     ";
        ////正则表达式
        //String pattern = "(（乙方）:)(.*?)( )"; //Java正则表达式以括号分组，第一个括号表示以"（乙方）:"开头，第三个括号表示以" "(空格)结尾，中间括号为目标值，
        //// 创建 Pattern 对象
        //Pattern r = Pattern.compile(pattern);
        //// 创建 matcher 对象
        //Matcher m = r.matcher(line);
        //while (m.find()) {
        //    /*
        //     自动遍历打印所有结果   group方法打印捕获的组内容，以正则的括号角标从1开始计算，我们这里要第2个括号里的
        //     值， 所以取 m.group(2)， m.group(0)取整个表达式的值，如果越界取m.group(4),则抛出异常
        //   */
        //    System.out.println("Found value: " + m.group(2));
        //}

    }

}
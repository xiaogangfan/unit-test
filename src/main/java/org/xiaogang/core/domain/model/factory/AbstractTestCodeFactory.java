package org.xiaogang.core.domain.model.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.ModelEnum;
import org.xiaogang.util.StringUtil;

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

    public static AbstractTestCodeFactory create(ModelEnum modelEnum, JavaSourceFile jsf) {
        AbstractTestCodeFactory factory = null;
        if (modelEnum == ModelEnum.DDD_Model) {
            return new DDDDomainTestCodeFactory(jsf);
        }
        return new DDDDomainTestCodeFactory(jsf);
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
            sb.append(writeInvoke(ModelEnum.DDD_Model, method));
            sb.append(space8 + verify(ModelEnum.DDD_Model, method));
            sb.append(enter + space8 + "// Write the Assert code");
            sb.append(writeAssert(ModelEnum.DDD_Model, method));
            sb.append(enter + space4 + "}");
        }
        return sb.toString();
    }

    protected abstract String initInstance();

    protected String verify(ModelEnum ddd_model, Method method) {
        return "";
    }

    protected String writeInvoke(ModelEnum ddd_model, Method method) {
        StringBuilder sb = new StringBuilder();
        return "";
    }

    protected String writeAssert(ModelEnum ddd_model, Method method) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
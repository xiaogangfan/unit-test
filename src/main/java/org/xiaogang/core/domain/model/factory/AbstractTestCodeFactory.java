package org.xiaogang.core.domain.model.factory;

import org.apache.commons.collections.CollectionUtils;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.ModelEnum;

import java.util.*;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-09 6:01 PM
 */
public abstract class AbstractTestCodeFactory {
    public static final String sep = "; \n";
    public static final String enter = "\n";
    public static final String impo = "import";
    public static final String tab = "  ";
    public static final String space4 = "    ";
    public static final String space8 = "        ";

    protected JavaSourceFile jsf = null;

    protected String pkg;
    protected Set<String> importList = new HashSet<>();
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
        setClassBody();

        return generateTestFileString();
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
                sb.append(s.trim() + enter);
            }
        }
        sb.append(classHeader + "{");
        sb.append(enter);
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
        for (Method method : jsf.getMethodList()) {
            sb.append(enter + space4 + "@Test");
            sb.append(enter + space4 + "public void test " + method.getName().substring(0, 1).toUpperCase() + method.getName().substring(1) + "(){ " + enter);
            sb.append(invoke(ModelEnum.DDD_Model, method));
            sb.append(space8 + verify(ModelEnum.DDD_Model, method));
            sb.append(enter + space8 + "//Write the Assert code" + enter);
            sb.append(enter + space4 + "}");
        }
        return sb.toString();
    }

    protected String verify(ModelEnum ddd_model, Method method) {
        return "";
    }

    protected String invoke(ModelEnum ddd_model, Method method) {
        StringBuilder sb = new StringBuilder();
        //sb.append(jsf.getName()+" "+)
        return "";
    }
}
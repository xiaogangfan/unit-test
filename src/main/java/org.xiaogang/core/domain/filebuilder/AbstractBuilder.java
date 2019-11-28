package org.xiaogang.core.domain.filebuilder;

import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.Method;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 8:41 PM
 */
public abstract class AbstractBuilder {

    private JavaSourceFile javaSourceFile;

    String getContent() {
        StringBuffer stringBuffer = new StringBuffer();

        return stringBuffer.toString();
    }

    protected StringBuilder generatePackage() {
        return new StringBuilder(javaSourceFile.getPkg());
    }

    protected StringBuilder generateImports() {
        return null;
    }

    protected StringBuilder generateMethod() {
        StringBuilder sb = new StringBuilder();
        for (Method method : javaSourceFile.getMethodList()) {
            sb.append("public void test" + method.getName() + "(){ ");
            sb.append("}");
        }
        return null;
    }

}
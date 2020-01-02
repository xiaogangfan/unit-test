package org.xiaogang.core.domain.model.factory;

import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.Method;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-09 6:01 PM
 */
public class JmockitCodeFactory extends AbstractTestCodeFactory {
    public JmockitCodeFactory(JavaSourceFile jsf) {
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
            sb.append(enter + space8 + jsf.getVarName() + " = new " + jsf.getName() + "()" + sep);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sb.append(enter + space4 + "}");
        return sb.toString();
    }

    public JmockitCodeFactory() {
        super();
    }

    @Override
    protected String writeInvoke(Method method) {
        return super.writeInvoke(method);
    }

    @Override
    protected String writeAssert(Method method) {
        return super.writeAssert(method);
    }

}
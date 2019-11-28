package org.xiaogang.core.domain.model.factory;

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
            importList.add("import " + jsf.getPkg()+"."+jsf.getName()+ sep);
            importList.add("import com.alibaba.cro.osm.core.utils.test.ObjectTestUtil" + sep);
        }

        StringBuilder sb = new StringBuilder();
        //String lowerCase = jsf.getName().toLowerCase();
        //
        //sb.append(space8 +
        //    jsf.getName() + " " + lowerCase + " = ObjectTestUtil.newObjectWithPropertiesValue("+jsf.getName()+".class)" + sep);
        //String param = "";
        //for (int i = 0; i < method.getParamList().size(); i++) {
        //    param += "ObjectTestUtil.newObjectWithPropertiesValue("+jsf.getName()+".class)
        //}
        //sb.append(space8 +lowerCase + "." + method.getName() +"{}"+ sep);
        return sb.toString();
    }
}
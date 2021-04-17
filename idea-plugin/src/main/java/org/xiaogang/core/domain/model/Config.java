package org.xiaogang.core.domain.model;

//import com.google.common.collect.Lists;s
//import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 右键弹出的配置项，可以指定要生成的methods、fields
 */
public class Config {
    List<String> methodList = new ArrayList<>(16);
    List<String> methodNameList = new ArrayList<>(16);

    public Config() {
    }

    public List<String> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<String> methodList) {
        this.methodList = methodList;
    }

    public List<String> getMethodNameList() {
        return methodNameList;
    }

    public void setMethodNameList(List<String> methodNameList) {
        this.methodNameList = methodNameList;
    }
}

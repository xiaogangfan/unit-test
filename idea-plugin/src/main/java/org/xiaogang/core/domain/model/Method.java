package org.xiaogang.core.domain.model;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.Type;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 7:10 PM
 */
public class Method {
    private String name;
    /**
     * 返回值类型
     */
    private String returnType;
    private Type type;
    private String body;
    private Optional<BlockStmt> originBody;

    private NodeList<Parameter> paramList;
    private MethodDeclaration methodDeclaration;


    public Method() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Optional<BlockStmt> getOriginBody() {
        return originBody;
    }

    public void setOriginBody(Optional<BlockStmt> originBody) {
        this.originBody = originBody;
    }

    public NodeList<Parameter> getParamList() {
        return paramList;
    }

    public void setParamList(NodeList<Parameter> paramList) {
        this.paramList = paramList;
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }
}

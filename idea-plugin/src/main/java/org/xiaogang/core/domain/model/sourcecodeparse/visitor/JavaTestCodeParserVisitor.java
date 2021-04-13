package org.xiaogang.core.domain.model.sourcecodeparse.visitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaTestCodeParser;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 6:58 PM
 */
public class JavaTestCodeParserVisitor extends VoidVisitorAdapter<JavaTestCodeParser> {
    @Override
    public void visit(MethodDeclaration n, JavaTestCodeParser arg) {
        Method method = new Method();
        method.setBody(n.getBody().toString());
        method.setOriginBody(n.getBody());
        method.setName(n.getName().toString());
        method.setParamList(n.getParameters());
        method.setReturnType(n.getType().asString());
        method.setType(n.getType());
        method.setMethodDeclaration(n);
        NodeList<TypeParameter> typeParameters = n.getTypeParameters();
        Iterator<TypeParameter> iterator = typeParameters.iterator();
        while (iterator.hasNext()) {
            TypeParameter typeParameter = iterator.next();
            System.out.println();
        }
        if (CollectionUtils.isEmpty(arg.getMethodList())) {
            arg.setMethodList(Lists.newArrayList(method));
        } else {
            List<Method> methodList = arg.getMethodList();
            methodList.add(method);
            arg.setMethodList(methodList);
        }

        if (CollectionUtils.isEmpty(arg.getMethodDeclarationList())) {
            arg.setMethodDeclarationList(Lists.newArrayList(n));
            HashMap<String, MethodDeclaration> objectObjectHashMap = Maps.newHashMap();
            objectObjectHashMap.put(arg.generateMethodSign(method), n);
            arg.setMethodDeclarationMap(objectObjectHashMap);
        } else {
            arg.getMethodDeclarationMap().put(arg.generateMethodSign(method), n);
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, JavaTestCodeParser arg) {
        arg.setName(n.getNameAsString());
        arg.setClassOrInterfaceDeclaration(n);
        super.visit(n, arg);
    }

    @Override
    public void visit(PackageDeclaration n, JavaTestCodeParser arg) {
        arg.setPkg(n.getNameAsString());
        arg.setPackageDeclaration(n);
        super.visit(n, arg);
    }

    @Override
    public void visit(final FieldDeclaration n, JavaTestCodeParser arg) {
        List<VariableDeclarator> variableDeclaratorList = Lists.newArrayList();
        Iterator<VariableDeclarator> iterator = n.getVariables().iterator();
        while (iterator.hasNext()) {
            VariableDeclarator entry = iterator.next();
            variableDeclaratorList.add(entry);
        }
        if (arg.getFieldList() == null || arg.getFieldList().size() == 0) {
            arg.setFieldList(variableDeclaratorList);
        } else {
            arg.getFieldList().addAll(variableDeclaratorList);
        }

        super.visit(n, arg);
    }

    @Override
    public void visit(final CompilationUnit n, JavaTestCodeParser arg) {
        arg.setCompilationUnit(n);
        n.getImports().forEach(p -> {
            if (p.getNameAsString().contains("lombok")) {
                return;
            }
            // 解决javaparser在处理import中带*的问题
            int i = p.getName().asString().lastIndexOf(".");
            String substring = p.getName().asString().substring(i + 1, i + 2);
            if (StringUtils.equals(substring, substring.toLowerCase())) {
                arg.getImportList().add("import " + (p.isStatic() ? "static" : "") + p.getName() + ".*;");
            } else {
                arg.getImportList().add("import " + (p.isStatic() ? "static " : "") + p.getName() + ";");
            }

        });
        super.visit(n, arg);
    }

    @Override
    public void visit(final ReturnStmt n, JavaTestCodeParser arg) {
        n.getExpression().ifPresent(l -> l.accept(this, arg));
        n.getComment().ifPresent(l -> l.accept(this, arg));
        super.visit(n, arg);
    }
}
package org.xiaogang.core.domain.model.sourcecodeparse.visitor;

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
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaSourceCodeParser;
import org.xiaogang.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 6:58 PM
 */
public class JavaSourceCodeParserVisitor extends VoidVisitorAdapter<JavaSourceCodeParser> {
    private Integer parseTimes = 0;

    @Override
    public void visit(MethodDeclaration n, JavaSourceCodeParser arg) {
        if (!canParse(parseTimes)) {
            return;
        }
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
        parseTimes++;
        super.visit(n, arg);
    }

    private boolean canParse(Integer parseTimes) {
        if (parseTimes > 6) {
            return false;
        }
        return true;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, JavaSourceCodeParser arg) {
        if (!canParse(parseTimes)) {
            return;
        }
        arg.setName(n.getNameAsString());
        parseTimes++;
        super.visit(n, arg);
    }

    @Override
    public void visit(PackageDeclaration n, JavaSourceCodeParser arg) {
        if (!canParse(parseTimes)) {
            return;
        }
        arg.setPkg(n.getNameAsString());
        parseTimes++;
        super.visit(n, arg);
    }

    @Override
    public void visit(final FieldDeclaration n, JavaSourceCodeParser arg) {
        if (!canParse(parseTimes)) {
            return;
        }
        List<VariableDeclarator> variableDeclaratorList = Lists.newArrayList();
        Iterator<com.github.javaparser.ast.body.VariableDeclarator> iterator = n.getVariables().iterator();
        while (iterator.hasNext()) {
            com.github.javaparser.ast.body.VariableDeclarator entry = iterator.next();
            variableDeclaratorList.add(entry);
        }
        if (arg.getFieldList() == null || arg.getFieldList().size() == 0) {
            arg.setFieldList(variableDeclaratorList);
        } else {
            arg.getFieldList().addAll(variableDeclaratorList);
        }
        parseTimes++;
        super.visit(n, arg);
    }

    @Override
    public void visit(final CompilationUnit n, JavaSourceCodeParser arg) {
        if (!canParse(parseTimes)) {
            return;
        }

        n.getImports().forEach(p -> {
            if (p.getNameAsString().contains("lombok")) {
                return;
            }
            // 解决javaparser在处理import中带*的问题
            int i = p.getName().asString().lastIndexOf(".");
            String substring = p.getName().asString().substring(i + 1, i + 2);
            if (substring.equals(substring.toLowerCase())) {
                arg.getImportList().add("import " + (p.isStatic() ? "static" : "") + p.getName() + ".*;");
            } else {
                arg.getImportList().add("import " + (p.isStatic() ? "static " : "") + p.getName() + ";");
            }

        });
        parseTimes++;
        super.visit(n, arg);
    }

    @Override
    public void visit(final ReturnStmt n, JavaSourceCodeParser arg) {
        if (!canParse(parseTimes)) {
            return;
        }
        n.getExpression().ifPresent(l -> l.accept(this, arg));
        n.getComment().ifPresent(l -> l.accept(this, arg));
        parseTimes++;
        super.visit(n, arg);
    }

}
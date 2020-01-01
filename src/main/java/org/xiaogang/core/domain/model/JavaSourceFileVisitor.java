package org.xiaogang.core.domain.model;

import java.util.Iterator;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 6:58 PM
 */
public class JavaSourceFileVisitor extends VoidVisitorAdapter<JavaSourceFile> {
    @Override
    public void visit(MethodDeclaration n, JavaSourceFile arg) {
        Method method = new Method();
        method.setBody(n.getBody().toString());
        method.setName(n.getName().toString());
        method.setParamList(n.getParameters());
        method.setReturnType(n.getType().asString());
        method.setType(n.getType());
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
        super.visit(n, arg);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, JavaSourceFile arg) {
        arg.setName(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(PackageDeclaration n, JavaSourceFile arg) {
        arg.setPkg(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(final FieldDeclaration n, JavaSourceFile arg) {
        List<VariableDeclarator> variableDeclaratorList = Lists.newArrayList();
        Iterator<com.github.javaparser.ast.body.VariableDeclarator> iterator = n.getVariables().iterator();
        while (iterator.hasNext()) {
            com.github.javaparser.ast.body.VariableDeclarator entry = iterator.next();
            entry.getName();
            variableDeclaratorList.add(new VariableDeclarator().setName(entry.getName().toString()));
        }
        arg.setFieldList(variableDeclaratorList);
        n.getVariables().forEach((p) -> {
            p.accept(this, arg);
        });
        n.getAnnotations().forEach((p) -> {
            p.accept(this, arg);
        });
        n.getComment().ifPresent((l) -> {
            l.accept(this, arg);
        });
        super.visit(n, arg);
    }

    @Override
    public void visit(final CompilationUnit n, JavaSourceFile arg) {

        n.getImports().forEach(p -> {
            if (p.getNameAsString().contains("lombok")) {
                return;
            }
            if (p.getNameAsString().contains("*")) {
                arg.getImportList().add("import " + p.getName() + "*;");
                return;
            }
            arg.getImportList().add("import " + p.getName() + ";");
        });
        //        n.getModule().ifPresent(l -> l.accept(this, arg));
        //        n.getPackageDeclaration().ifPresent(l -> l.accept(this, arg));
        //        n.getTypes().forEach(p -> p.accept(this, arg));
        //        n.getComment().ifPresent(l -> l.accept(this, arg));
        super.visit(n, arg);
    }

    @Override
    public void visit(final ReturnStmt n, JavaSourceFile arg) {
        n.getExpression().ifPresent(l -> l.accept(this, arg));
        n.getComment().ifPresent(l -> l.accept(this, arg));
        super.visit(n, arg);
    }
}
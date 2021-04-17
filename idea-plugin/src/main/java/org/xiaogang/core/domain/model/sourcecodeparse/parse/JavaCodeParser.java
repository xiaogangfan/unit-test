package org.xiaogang.core.domain.model.sourcecodeparse.parse;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.util.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 5:14 PM
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Accessors(chain = true)
public class JavaCodeParser {

    /**
     * customize parse
     */
    private String pathName;
    private String absDir;
    private String pkg;
    private String name;
    private String className;
    private String extendsClass;
    private String implementInterface;
    private Set<String> importList = new HashSet<>();

    private List<Method> methodList;

    /**
     * from gighub javaparser
     */
    private PackageDeclaration packageDeclaration;
    private CompilationUnit compilationUnit;
    private ClassOrInterfaceDeclaration classOrInterfaceDeclaration;
    private List<VariableDeclarator> fieldList;
    private List<MethodDeclaration> methodDeclarationList;
    private Map<String, MethodDeclaration> methodDeclarationMap;

    public String getVarName() {
        return StringUtil.firstLower(getName());
    }

    /**
     * 生成方法签名
     *
     * @param method
     * @return
     */
    public String generateMethodSign(Method method) {
        //return method.getName() +
        //    (CollectionUtils.isEmpty(method.getParamList()) ? "()" : "(" + method.getParamList().stream().map(
        //        t -> {
        //            return (t.getType().asString() + " " + t.getName().asString());
        //        }).collect(Collectors.toList()).stream().collect(
        //        Collectors.joining(",")) + "）")
        //    + ":" + method.getReturnType();
        return method.getName();
    }

    public JavaCodeParser() {
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getAbsDir() {
        return absDir;
    }

    public void setAbsDir(String absDir) {
        this.absDir = absDir;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getExtendsClass() {
        return extendsClass;
    }

    public void setExtendsClass(String extendsClass) {
        this.extendsClass = extendsClass;
    }

    public String getImplementInterface() {
        return implementInterface;
    }

    public void setImplementInterface(String implementInterface) {
        this.implementInterface = implementInterface;
    }

    public Set<String> getImportList() {
        return importList;
    }

    public void setImportList(Set<String> importList) {
        this.importList = importList;
    }

    public List<Method> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<Method> methodList) {
        this.methodList = methodList;
    }

    public PackageDeclaration getPackageDeclaration() {
        return packageDeclaration;
    }

    public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
        this.packageDeclaration = packageDeclaration;
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public void setCompilationUnit(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public ClassOrInterfaceDeclaration getClassOrInterfaceDeclaration() {
        return classOrInterfaceDeclaration;
    }

    public void setClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        this.classOrInterfaceDeclaration = classOrInterfaceDeclaration;
    }

    public List<VariableDeclarator> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<VariableDeclarator> fieldList) {
        this.fieldList = fieldList;
    }

    public List<MethodDeclaration> getMethodDeclarationList() {
        return methodDeclarationList;
    }

    public void setMethodDeclarationList(List<MethodDeclaration> methodDeclarationList) {
        this.methodDeclarationList = methodDeclarationList;
    }

    public Map<String, MethodDeclaration> getMethodDeclarationMap() {
        return methodDeclarationMap;
    }

    public void setMethodDeclarationMap(Map<String, MethodDeclaration> methodDeclarationMap) {
        this.methodDeclarationMap = methodDeclarationMap;
    }
}
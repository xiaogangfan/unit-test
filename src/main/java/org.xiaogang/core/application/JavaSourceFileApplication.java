package org.xiaogang.core.application;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.google.common.collect.Lists;

import org.mozilla.javascript.annotations.JSFunction;
import org.xiaogang.core.domain.model.JavaSourceFile;
import org.xiaogang.core.domain.model.MethodVisitor;
import org.xiaogang.core.domain.model.ModelEnum;
import org.xiaogang.core.domain.model.factory.AbstractTestCodeFactory;

import java.io.*;
import java.util.List;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 5:25 PM
 */
public class JavaSourceFileApplication {

    public static void findFileList(File file, List<String> fileNames) {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            fileNames.add(file.getPath());
            return;
        }
        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {
            File tempFile = new File(file, files[i]);
            if (tempFile.isFile()) {
                if (tempFile.getName().endsWith(".java")) {
                    fileNames.add(tempFile.getPath());
                }
            } else {
                findFileList(tempFile, fileNames);
            }
        }
    }

    public static List<JavaSourceFile> parse(List<String> fileNames) {
        List<JavaSourceFile> javaSourceFileList = Lists.newArrayList();
        try {
            for (String fileName : fileNames) {
                FileInputStream in = new FileInputStream(fileName);
                CompilationUnit cu = JavaParser.parse(in);
                JavaSourceFile jsf = new JavaSourceFile();
                jsf.setPathName(fileName);
                cu.accept(new MethodVisitor(), jsf);
                javaSourceFileList.add(jsf);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("parse erroe", e);
        }
        return javaSourceFileList;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> fileNames = Lists.newArrayList();
        JavaSourceFileApplication.findFileList(
                new File(
                        "/Users/xiaogangfan/Documents/ddd/auto-unittest-code/src/main/shishang/action/AutoGenerationTestCode"
                                + ".java")
                , fileNames
        );
        List<JavaSourceFile> javaSourceFileList = parse(fileNames);

        //System.out.println(JSON.toJSON(fileNames));

        generateFile(javaSourceFileList);
    }

    private static void generateFile(List<JavaSourceFile> javaSourceFileList) {
        PrintWriter pw = null;
        BufferedWriter bw = null;
        for (JavaSourceFile javaSourceFile : javaSourceFileList) {

            String testFile = javaSourceFile.getPathName().replace("/main/", "/test/");
            int i = testFile.lastIndexOf("/");
            String dir = testFile.substring(0, i);
            File parentDir = new File(dir);
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            int i1 = testFile.lastIndexOf(".");
            String testFileName = testFile.substring(0, i1) + "Test.java";
            File file = new File(testFileName);

            String testFileString = generateTestFileString(javaSourceFile);
            try {
                if (!file.exists()) {    //判断是否存在java文件
                    file.createNewFile();    //不存在则创建
                }
                pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
                bw = new BufferedWriter(pw);
                bw.write(testFileString);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (pw != null) {
                        pw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static String generateTestFileString(JavaSourceFile javaSourceFile) {
        AbstractTestCodeFactory factory = AbstractTestCodeFactory.create(ModelEnum.DDD_Model, javaSourceFile);
        return factory.createFileString();
    }

    public static void generateFiles(String path) {
        List<String> fileNames = Lists.newArrayList();
        JavaSourceFileApplication.findFileList(
                new File(path)
                , fileNames
        );
        List<JavaSourceFile> javaSourceFileList = parse(fileNames);

        generateFile(javaSourceFileList);
    }
}
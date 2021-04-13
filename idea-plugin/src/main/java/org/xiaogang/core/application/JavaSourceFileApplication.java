package org.xiaogang.core.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.google.common.collect.Lists;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.xiaogang.action.dialog.SampleDialogWrapper;
import org.xiaogang.core.domain.model.Config;
import org.xiaogang.core.domain.model.factory.AbstractTestCodeFactory;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaSourceCodeParser;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaTestCodeParser;
import org.xiaogang.core.domain.model.sourcecodeparse.visitor.JavaSourceCodeParserVisitor;
import org.xiaogang.core.domain.model.sourcecodeparse.visitor.JavaTestCodeParserVisitor;

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

    public static JavaSourceCodeParser parseSourceCodeFile(String fileName) {
        JavaSourceCodeParser jsf = new JavaSourceCodeParser();
        try {
            FileInputStream in = new FileInputStream(fileName);
            CompilationUnit cu = JavaParser.parse(in);
            jsf.setPathName(fileName);

            cu.accept(new JavaSourceCodeParserVisitor(), jsf);
            //                jsf.setClassName()
            return jsf;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("parse erroe", e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> fileNames = Lists.newArrayList();
        JavaSourceFileApplication.findFileList(
            new File(
                "/Users/xiaogangfan/Documents/ddd/auto-unittest-code/src/main/shishang/action/AutoGenerationTestCode"
                    + ".java")
            , fileNames
        );
        //JavaSourceFile javaSourceFile = parse(fileNames);

        //System.out.println(JSON.toJSON(fileNames));

        //generateFile(javaSourceFile);
    }

    private static void generateFile(AnActionEvent e,
        JavaSourceCodeParser javaSourceCodeParser,
        JavaTestCodeParser javaTestCodeParser, Config config) {
        String testFileString = generateTestFileString(javaSourceCodeParser, javaTestCodeParser, config);
        writeFile(e, javaSourceCodeParser, testFileString);

    }

    private static void writeFile(AnActionEvent event,
        JavaSourceCodeParser javaSourceCodeParser, String testFileString) {
        String testFile = javaSourceCodeParser.getPathName().replace("/main/", "/test/");
        int i = testFile.lastIndexOf("/");
        String dir = testFile.substring(0, i);

        File parentDir = new File(dir);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        int i1 = testFile.lastIndexOf(".");
        String testFileName = testFile.substring(0, i1) + "Test.java";
        File file = new File(testFileName);

        PrintWriter pw = null;
        BufferedWriter bw = null;
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

        // Format
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(event.getData(PlatformDataKeys.PROJECT), testFileName,
            GlobalSearchScope.allScope(event.getData(PlatformDataKeys.PROJECT)));
        CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(event.getData(PlatformDataKeys.PROJECT));
        try {
            codeStyleManager.reformat(psiFiles[0]);
        } catch (Exception e) {
        }

        try {
            VirtualFileManager manager = VirtualFileManager.getInstance();
            VirtualFile virtualFile = manager
                .refreshAndFindFileByUrl(VfsUtil.pathToUrl(testFileName));

            VirtualFile finalVirtualFile = virtualFile;
            ApplicationManager.getApplication()
                .invokeLater(
                    () -> FileEditorManager.getInstance(event.getProject()).openFile(finalVirtualFile, true,
                        true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateTestFileString(JavaSourceCodeParser javaSourceCodeParser,
        JavaTestCodeParser javaTestCodeParser, Config config) {
        AbstractTestCodeFactory factory = AbstractTestCodeFactory.create(javaSourceCodeParser, javaTestCodeParser,
            config);
        return factory.createFileString();
    }

    public static boolean generateFiles(AnActionEvent e, String path) {
        //List<String> fileNames = Lists.newArrayList();
        //findFileList(
        //    new File(path)
        //    , fileNames
        //);
        JavaSourceCodeParser javaSourceCodeParser = parseSourceCodeFile(path);
        JavaTestCodeParser javaTestCodeParser = parseTargeTestFile(path);

        Config config = new Config();
        SampleDialogWrapper sampleDialogWrapper = new SampleDialogWrapper(javaSourceCodeParser, config);
        boolean isOk = sampleDialogWrapper.showAndGet();
        if (isOk) {
            generateFile(e, javaSourceCodeParser, javaTestCodeParser, config);
            return true;
        } else {
            return false;
        }

    }

    private static JavaTestCodeParser parseTargeTestFile(String fileName) {
        String testFile = fileName.replace("/main/", "/test/");
        int i = testFile.lastIndexOf("/");
        int i1 = testFile.lastIndexOf(".");
        String testFileName = testFile.substring(0, i1) + "Test.java";
        File file = new File(testFileName);
        if (!new File(testFileName).exists()) {
            return null;
        }
        JavaTestCodeParser jsf = new JavaTestCodeParser();
        try {
            FileInputStream in = new FileInputStream(file);
            CompilationUnit cu = JavaParser.parse(in);
            jsf.setPathName(testFile);

            cu.accept(new JavaTestCodeParserVisitor(), jsf);
            //                jsf.setClassName()
            return jsf;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("parse erroe", e);
        }
    }
}
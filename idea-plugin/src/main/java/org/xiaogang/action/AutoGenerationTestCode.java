package org.xiaogang.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import org.xiaogang.core.application.JavaSourceFileApplication;

import javax.swing.*;
import java.awt.*;

/**
 * created by xiaogangfan on 2019/9/7.
 */
public class AutoGenerationTestCode extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String result = "成功";
        try {
            String path = queryPath(e);
            // Generate file
            generateFiles(e, path);

        } catch (Exception e1) {
            e1.printStackTrace();
            result = "失败：" + e1.getMessage();
            Messages.showMessageDialog(
                result
                , "生成失败："
                , Messages.getInformationIcon()
            );

        }

    }

    private boolean generateFiles(AnActionEvent e, String path) {
        return JavaSourceFileApplication.generateFiles(e, path);
    }

    private String queryPath(AnActionEvent e) {

        // 获取当前文件对象
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, e.getProject());
        String path = psiFile.getViewProvider().getVirtualFile().getPath();
        return path;

        //if ("EditorPopup".equals(e.getPlace())) {
        //    Project project = e.getProject();
        //
        //    // 获取当前文件对象
        //    Editor editor = e.getData(PlatformDataKeys.EDITOR);
        //    PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        //    String path = psiFile.getViewProvider().getVirtualFile().getPath();
        //    return path;
        //} else if ("ProjectViewPopup".equals(e.getPlace())) {
        //    return e.getProject().getBasePath();
        //}

        //throw new RuntimeException("获取路径错误");
    }

    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("testing");
        label.setPreferredSize(new Dimension(100, 100));
        dialogPanel.add(label, BorderLayout.CENTER);

        return dialogPanel;
    }
}

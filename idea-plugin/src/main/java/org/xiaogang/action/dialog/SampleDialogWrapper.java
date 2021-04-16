package org.xiaogang.action.dialog;

import com.intellij.openapi.ui.DialogWrapper;
//import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.Nullable;
import org.xiaogang.core.domain.model.Config;
import org.xiaogang.core.domain.model.Method;
import org.xiaogang.core.domain.model.sourcecodeparse.parse.JavaSourceCodeParser;
import org.xiaogang.util.CollectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.stream.Collectors;

public class SampleDialogWrapper extends DialogWrapper {
    JavaSourceCodeParser javaSourceCodeParser;
    Config config;

    public SampleDialogWrapper(JavaSourceCodeParser javaSourceCodeParser, Config config) {
        super(true); // use current window as parent
        this.javaSourceCodeParser = javaSourceCodeParser;
        this.config = config;
        init();
        setTitle("Test DialogWrapper");

    }

    public void setJavaSourceCodeParser(JavaSourceCodeParser javaSourceCodeParser) {
        this.javaSourceCodeParser = javaSourceCodeParser;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel();

        dialogPanel.setLayout(new GridLayout(javaSourceCodeParser.getMethodList().size(), 1));
        //JButton jButton = new JButton("全选/取消");
        //jButton.addActionListener(
        //    new ActionListener() {
        //        @Override
        //        public void actionPerformed(ActionEvent e) {
        //            e.getActionCommand();
        //        }
        //    }
        //);
        //dialogPanel.add(jButton,1);

        for (Method method : javaSourceCodeParser.getMethodList()) {
            JCheckBox jCheckBox = wrapJcheckBox(method);
            ItemListener itemListener = new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    Object obj = e.getItem();
                    if (obj.equals(jCheckBox)) {
                        if (jCheckBox.isSelected()) {
                            config.getMethodList().add(jCheckBox.getText());
                            config.getMethodNameList().add(method.getName());
                        } else {
                            config.getMethodList().remove(jCheckBox.getText());
                            config.getMethodNameList().remove(method.getName());
                        }
                    }
                }
            };
            jCheckBox.addItemListener(itemListener);
            dialogPanel.add(jCheckBox);
        }

        return dialogPanel;
    }

    private JCheckBox wrapJcheckBox(Method method) {

        return new JCheckBox(method.getName() +
            (CollectionUtils.isEmpty(method.getParamList()) ? "()" : "(" + method.getParamList().stream().map(
                t -> {
                    return (t.getType().asString() + " " + t.getName().asString());
                }).collect(Collectors.toList()).stream().collect(
                Collectors.joining(",")) + "）")
            + ":" + method.getReturnType());
    }

}


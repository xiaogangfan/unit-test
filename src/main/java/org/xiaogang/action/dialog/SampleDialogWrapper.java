package org.xiaogang.action.dialog;


import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class SampleDialogWrapper extends DialogWrapper {

    public SampleDialogWrapper() {
        super(true); // use current window as parent
        init();
        setTitle("Test DialogWrapper");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("testing");
        label.setPreferredSize(new Dimension(100, 100));
        dialogPanel.add(label, BorderLayout.CENTER);

        return dialogPanel;
    }
    public static void main(String[] args) {
        JPanel jPanel = new JPanel();
        // 主窗体
        JFrame f = new JFrame("LoL");


        // 主窗体设置大小
        f.setSize(400, 300);

        // 主窗体设置位置
        f.setLocation(200, 200);

        // 主窗体中的组件设置为绝对定位
        f.setLayout(null);

        // 按钮组件
        JButton b = new JButton("一键秒对方基地挂");

        // 同时设置组件的大小和位置
        b.setBounds(50, 50, 280, 30);

        // 把按钮加入到主窗体中
        f.add(b);

        // 关闭窗体的时候，退出程序
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 让窗体变得可见
        f.setVisible(true);

    }

}


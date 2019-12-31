package org.xiaogang.action.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;


public class Main extends JFrame {

    //组件区
    final JPanel topPanel = new JPanel();
    final JPanel bottomPanel = new JPanel();
    final JPanel rightPanel = new JPanel();
    final JScrollPane topPane = new JScrollPane(topPanel);
    final JSplitPane splitLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPane, bottomPanel);

    final JTextArea text = new JTextArea();

    final JButton sendBtn = new JButton("发送消息");
    final JButton saveBtn = new JButton("保存消息");
    final JButton readBtn = new JButton("读入消息");
    final JButton clearBtn = new JButton("清空消息");

    final JLabel tipLabel = new JLabel("正常");

    public Main() {
        //组件设置区
        this.add(splitLeft, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        splitLeft.setDividerLocation(400);
        rightPanel.setPreferredSize(new Dimension(100, 0));

        rightPanel.add(sendBtn);
        rightPanel.add(saveBtn);
        rightPanel.add(readBtn);
        rightPanel.add(clearBtn);
        rightPanel.add(tipLabel);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(text, BorderLayout.CENTER);


        //事件绑定区

        //发送消息
        sendBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = new Date().toLocaleString();
                String text2 = "<html><body><font color=#c3c3c3>" + text.getText().replaceAll("\n", "<br/>") + "</font></body></html>";
                setMsg(text1, text2);
            }
        });

        //保存消息
        saveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("pro.dat");
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    tipLabel.setText("保存失败");
                    return;
                }

                FileWriter rw = null;
                try {
                    rw = new FileWriter(file);
                } catch (IOException e1) {
                    tipLabel.setText("文件打开失败");
                    return;
                } finally {
                    for (int i = 0; i < topPanel.getComponentCount(); i++) {
                        JLabel label = (JLabel) topPanel.getComponent(i);
                        try {
                            rw.write(label.getText() + "\n");
                            //System.out.println(label.getText());
                        } catch (IOException e1) {
                            tipLabel.setText("写出错");
                            try {
                                rw.close();
                            } catch (IOException e2) {

                            }
                            return;
                        }
                    }

                    try {
                        rw.close();
                    } catch (IOException e1) {

                    }
                }

                tipLabel.setText("写成功：pro.dat");
            }
        });

        //读入消息

        readBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileReader fr = null;
                try {
                    fr = new FileReader("pro.dat");
                } catch (FileNotFoundException e1) {
                    tipLabel.setText("文件不存在");
                    return;
                }

                BufferedReader br = new BufferedReader(fr);
                while (true) {
                    String line1;
                    String line2;
                    try {
                        line1 = br.readLine();
                        line2 = br.readLine();
                    } catch (IOException e1) {
                        tipLabel.setText("读取异常");
                        return;
                    }

                    if (line1 == null || line2 == null) {
                        break;
                    } else {
                        setMsg(line1, line2);
                    }
                }
                tipLabel.setText("读取成功");
                try {
                    br.close();
                    fr.close();
                } catch (IOException e1) {

                }
                return;
            }
        });

        //清空消息

        clearBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                topPanel.removeAll();
                topPanel.updateUI();
            }
        });
    }

    //自定义函数区

    //发送消息的工具函数
    public void setMsg(String text1, String text2) {
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        label1.setText(text1);
        label2.setText(text2);
        label1.setForeground(Color.RED);
        topPanel.add(label1);
        topPanel.add(label2);
        text.setText("");
        topPanel.updateUI();
    }


    public static void start() {

        //窗口设置区
        JFrame frame = new Main();
        frame.setSize(800, 600);
        frame.setLocation(0, 0);
        frame.setBackground(Color.GRAY);
        frame.setTitle("我的窗口");
        frame.setVisible(true);
    }

}

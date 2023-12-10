package org.zju.view;

import org.zju.service.MessageClientService;
import org.zju.service.UserClientService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainInterface {
    private JFrame frame;
    private UserClientService userClientService;
    private final String name;

    public MainInterface(UserClientService userClientService) {
        this.userClientService = userClientService;
        name = userClientService.getUser().getName();
        // 创建 JFrame 实例
        frame = new JFrame(name);
        // Setting the width and height of frame
        frame.setSize(350, 220);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JButton showUsersButton = new JButton("显示在线用户列表");
        showUsersButton.setBounds(80, 20, 180, 25);
        panel.add(showUsersButton);
        showUsersButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userClientService.OnlineFriend();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton batchMessage = new JButton("群发消息");
        batchMessage.setBounds(80, 50, 180, 25);
        panel.add(batchMessage);
        batchMessage.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MessageClientService.CommonChat(name);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton privateMessage = new JButton("私聊消息");
        privateMessage.setBounds(80, 80, 180, 25);
        panel.add(privateMessage);
        privateMessage.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MessageClientService.PrivateChat(name);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton fileMessage = new JButton("发送文件");
        fileMessage.setBounds(80, 110, 180, 25);
        panel.add(fileMessage);
        fileMessage.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MessageClientService.FileTrans(name);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton exit = new JButton("退出系统");
        exit.setBounds(80, 140, 180, 25);
        panel.add(exit);
        exit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userClientService.Exit();
                    frame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


}

package org.zju.view;

import org.zju.pojo.User;
import org.zju.service.MessageClientService;
import org.zju.service.UserClientService;
import org.zju.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUI extends Frame {
    public static String name;
    public static String password;
    private static boolean loop = true;
    private static String key = "";
    private static UserClientService userClientService = new UserClientService();//登陆，注册

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Login Example");
        // Setting the width and height of frame
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        // 创建 JLabel
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);


        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(80, 100, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = userText.getText();
                password = new String(passwordText.getPassword());
                User user = new User(name, password);
                try {
                    login(user);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton registerButton = new JButton("注册");
        registerButton.setBounds(180, 100, 80, 25);
        panel.add(registerButton);
        registerButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

    }

    private static void login(User user) throws IOException, ClassNotFoundException {
        boolean check;
        try {
            check = userClientService.Check(user.getName(), user.getPassword());
        } catch (Exception e) {
            new LoginFailDialog("无法与服务器连接！");
            return;
        }
        if (check) {
            while (loop) {
                System.out.println("\n============QQ二级菜单============");
                System.out.println("\t\t 1 显示在线用户列表");
                System.out.println("\t\t 2 群发消息");
                System.out.println("\t\t 3 私聊消息");
                System.out.println("\t\t 4 发送文件");
                System.out.println("\t\t 9 退出系统");
                System.out.println("请输入你的选择");
                key = Utility.readString(1);
                switch (key) {
                    case "1" -> userClientService.OnlineFriend();
                    case "2" -> MessageClientService.CommonChat(name);
                    case "3" -> MessageClientService.PrivateChat(name);
                    case "4" -> MessageClientService.FileTrans(name);
                    case "9" -> {
                        System.out.println("=============客户端退出系统============");
                        userClientService.Exit();
                        loop = false;
                    }
                }
            }
        } else {
            new LoginFailDialog("登陆失败！");
        }
    }



}

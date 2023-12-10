package org.zju.view;

import org.zju.pojo.User;
import org.zju.service.MessageClientService;
import org.zju.service.UserClientService;
import org.zju.utils.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class LogIn extends Frame {
    public static String name;
    public static String password;
    private static boolean loop = true;
    private static String key = "";
    private static UserClientService userClientService = new UserClientService();//登陆，注册
    private static JFrame frame;

    public static void main(String[] args) {
        // 创建 JFrame 实例
        frame = new JFrame("登录");
        // Setting the width and height of frame
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        // 创建 JLabel
        JLabel userLabel = new JLabel("名字:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("密码:");
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
                new Register(userClientService);
            }
        });

    }

    private static void login(User user) throws IOException, ClassNotFoundException {
        boolean check;
        try {
            check = userClientService.Check(user.getName(), user.getPassword());
        } catch (Exception e) {
            new Dialog("无法与服务器连接！");
            return;
        }
        if (check) {
            frame.dispose();
            new MainInterface(userClientService);
        } else {
            new Dialog("登陆失败！");
        }
    }




}

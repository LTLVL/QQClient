package org.zju.view;

import org.zju.pojo.User;
import org.zju.service.UserClientService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Register {
    private String name;
    private Integer age;
    private String phone;
    private String password;
    private UserClientService userClientService;
    private final JFrame frame;


    public Register(UserClientService userClientService) {
        this.userClientService = userClientService;
        frame = new JFrame("注册");
        // Setting the width and height of frame
        frame.setSize(350, 230);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        // 创建 JLabel
        JLabel userLabel = new JLabel("名字:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel ageLabel = new JLabel("年龄:");
        ageLabel.setBounds(10, 50, 80, 25);
        panel.add(ageLabel);
        JTextField ageText = new JTextField(20);
        ageText.setBounds(100, 50, 165, 25);
        panel.add(ageText);

        JLabel phoneLabel = new JLabel("电话:");
        phoneLabel.setBounds(10, 80, 80, 25);
        panel.add(phoneLabel);
        JTextField phoneText = new JTextField(20);
        phoneText.setBounds(100, 80, 165, 25);
        panel.add(phoneText);


        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10, 110, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 110, 165, 25);
        panel.add(passwordText);


        JButton registerButton = new JButton("注册");
        registerButton.setBounds(140, 150, 80, 25);
        panel.add(registerButton);
        registerButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = userText.getText();
                age = Integer.valueOf(ageText.getText());
                phone = phoneText.getText();
                password = new String(passwordText.getPassword());
                User user = new User(null, name, age, phone, password);
                try {
                    if (userClientService.Register(user)) {
                        new Dialog("注册成功！");
                        frame.dispose();
                    } else {
                        new Dialog("注册失败！");
                    }
                } catch (Exception ex) {
                    new Dialog("无法与服务器连接！");
                }
            }
        });

    }
}

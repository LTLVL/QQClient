package org.zju.service;

import org.zju.pojo.Message;
import org.zju.pojo.MessageType;
import org.zju.pojo.User;
import org.zju.utils.StreamUtils;
import org.zju.utils.Utility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Date;

public class MessageClientService {
    public static void PrivateChat(String sender) throws IOException {
        JFrame frame = new JFrame("私聊消息");
        // Setting the width and height of frame
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        // 设置界面可见
        frame.setVisible(true);
        panel.setLayout(null);
        // 创建 JLabel
        JLabel userLabel = new JLabel("发送给：");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel msgLabel = new JLabel("消息内容：");
        msgLabel.setBounds(10, 50, 80, 25);
        panel.add(msgLabel);
        JTextField msgText = new JTextField(20);
        msgText.setBounds(100, 50, 165, 25);
        panel.add(msgText);

        // 创建私聊按钮
        JButton privateButton = new JButton("私聊");
        privateButton.setBounds(80, 100, 80, 25);
        panel.add(privateButton);
        privateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setSender(sender);
                message.setGetter(userText.getText());
                message.setContent(msgText.getText());
                message.setSendTime(new Date().toString());
                message.setMesType(MessageType.MESSAGE_CLIENT_PRIVATE_MESSAGE);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getThread(sender).getSocket().getOutputStream());
                    oos.writeObject(message);
                    frame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void CommonChat(String sender) throws IOException {
        JFrame frame = new JFrame("群发消息");
        // Setting the width and height of frame
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        // 设置界面可见
        frame.setVisible(true);
        panel.setLayout(null);
        // 创建 JLabel
        JLabel msgLabel = new JLabel("消息内容：");
        msgLabel.setBounds(10, 20, 80, 25);
        panel.add(msgLabel);
        JTextField msgText = new JTextField(20);
        msgText.setBounds(100, 20, 165, 25);
        panel.add(msgText);

        // 创建群发按钮
        JButton commonButton = new JButton("群发");
        commonButton.setBounds(80, 100, 80, 25);
        panel.add(commonButton);
        commonButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = msgText.getText();
                Message message = new Message();
                message.setSender(sender);
                message.setContent(msg);
                message.setSendTime(new Date().toString());
                message.setMesType(MessageType.MESSAGE_COMM_MES);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getThread(sender).getSocket().getOutputStream());
                    oos.writeObject(message);
                    frame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
    public static void FileTrans(String sender) throws IOException {
        JFrame frame = new JFrame("发送文件");
        // Setting the width and height of frame
        frame.setSize(450, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        // 设置界面可见
        frame.setVisible(true);
        panel.setLayout(null);
        // 创建 JLabel
        // 创建 JLabel
        JLabel userLabel = new JLabel("发送给：");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(150, 20, 165, 25);
        panel.add(userText);

        JLabel sendFileLabel = new JLabel("发送方文件的完整路径：");
        sendFileLabel.setBounds(10, 50, 150, 25);
        panel.add(sendFileLabel);
        JTextField sendText = new JTextField(20);
        sendText.setBounds(150, 50, 165, 25);
        panel.add(sendText);

        JLabel recFileLabel = new JLabel("接收方文件的完整路径：");
        recFileLabel.setBounds(10, 80, 150, 25);
        panel.add(recFileLabel);
        JTextField recText = new JTextField(20);
        recText.setBounds(150, 80, 165, 25);
        panel.add(recText);

        // 创建群发按钮
        JButton sendFileButton = new JButton("发送文件");
        sendFileButton.setBounds(150, 110, 100, 25);
        panel.add(sendFileButton);
        sendFileButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedInputStream bos = null;
                try {
                    bos = new BufferedInputStream(new FileInputStream(sendText.getText()));
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                byte[] bytes = new byte[0];
                try {
                    bytes = StreamUtils.streamToByteArray(bos);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Message message = new Message();
                message.setGetter(userText.getText());
                message.setSender(sender);
                message.setSendTime(new Date().toString());
                message.setMesType(MessageType.MESSAGE_CLIENT_FILE);
                message.setFileBytes(bytes);
                message.setFileLEN(bytes.length);
                message.setSenderPath(sendText.getText());
                message.setGetterPath(recText.getText());
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(ManageClientThread.getThread(sender).getSocket().getOutputStream());
                    oos.writeObject(message);
                    bos.close();
                    frame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}

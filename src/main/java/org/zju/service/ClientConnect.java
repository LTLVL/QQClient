package org.zju.service;

import org.zju.pojo.Message;
import org.zju.pojo.MessageType;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientConnect extends Thread {
    private Socket socket;
    private boolean loop = true;

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public ClientConnect(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (loop) {
            try {
                System.out.println("客户端和服务端建立连接");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    showAliveUsers(message);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_PRIVATE_MESSAGE)) {
                    receivePrivateMessage(message);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    socket.close();
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    receiveCommonMessage(message);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_FILE)) {
                    receiveFileMessage(message);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }

    private void receiveFileMessage(Message message) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(message.getGetterPath()));
        bos.write(message.getFileBytes());
        bos.flush();
        bos.close();
        JFrame frame = new JFrame("收到文件");
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
        JLabel userLabel = new JLabel("收到来自用户：" + message.getSender() + " 的文件："
                + message.getSenderPath());
        userLabel.setBounds(10, 20, 350, 25);
        panel.add(userLabel);
        JLabel userLabel1 = new JLabel("已存储在：" + message.getGetterPath());
        userLabel1.setBounds(10, 50, 350, 25);
        panel.add(userLabel1);

//        System.out.println("收到来自用户 " + message.getSender() + "的文件:"+message.getSenderPath());
//        System.out.println("保存到路径："+message.getGetterPath());
    }

    private void receivePrivateMessage(Message message) {
        String[] s = message.getContent().split(" ");
        JFrame frame = new JFrame("收到私聊消息");
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
        JLabel userLabel = new JLabel("收到来自用户：" + message.getSender() + " 的私聊消息：");
        userLabel.setBounds(10, 20, 350, 25);
        panel.add(userLabel);
        JLabel msgLabel = new JLabel(message.getContent());
        msgLabel.setBounds(10, 50, 350, 25);
        panel.add(msgLabel);
    }

    private void receiveCommonMessage(Message message) {
        String[] s = message.getContent().split(" ");
        JFrame frame = new JFrame("收到群聊消息");
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
        JLabel userLabel = new JLabel("收到来自用户：" + message.getSender() + " 的群发消息：");
        userLabel.setBounds(10, 20, 350, 25);
        panel.add(userLabel);
        JLabel msgLabel = new JLabel(message.getContent());
        msgLabel.setBounds(10, 50, 350, 25);
        panel.add(msgLabel);
    }

    private void showAliveUsers(Message message) {
        String[] s = message.getContent().split(" ");
        JFrame frame = new JFrame("在线用户列表");
        // Setting the width and height of frame
        frame.setSize(350, 40 + 40 * s.length);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        // 设置界面可见
        frame.setVisible(true);
        panel.setLayout(null);
        for (int i = 0; i < s.length; i++) {
            // 创建 JLabel
            JLabel userLabel = new JLabel("用户：" + s[i]);
            userLabel.setBounds(10, 20 * i, 80, 25);
            panel.add(userLabel);
        }
    }
}

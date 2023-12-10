package org.zju.service;

import lombok.Data;
import org.zju.pojo.Message;
import org.zju.pojo.MessageType;
import org.zju.pojo.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

@Data
public class UserClientService {
    private User user = new User();
    private Socket socket = new Socket();


    public boolean Check(String name, String passwd) throws IOException, ClassNotFoundException {
        user.setName(name);
        user.setPassword(passwd);
        boolean b = false;
        socket = new Socket(InetAddress.getLocalHost(), 9999);
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(user);


        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        Object o = ois.readObject();
        Message message = (Message) o;

        if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
            ClientConnect connect = new ClientConnect(socket);
            connect.start();
            ManageClientThread.Add(name, connect);

            b = true;
        } else {
            System.out.println("==========登陆失败==========");
            b = false;
        }
        return b;
    }

    public boolean Register(User user) throws IOException, ClassNotFoundException {
        socket = new Socket(InetAddress.getLocalHost(), 9999);
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(user);
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        Object o = ois.readObject();
        Message message = (Message) o;
        boolean b;

        if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
            ClientConnect connect = new ClientConnect(socket);
            connect.start();
            ManageClientThread.Add(user.getName(), connect);
            b = true;
        } else {
            System.out.println("==========注册失败==========");
            b = false;
        }
        return b;
    }

    public void OnlineFriend() throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getName());
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getThread(user.getName()).getSocket().getOutputStream());
        oos.writeObject(message);
    }

    public void Exit() throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getName());
        message.setGetter(user.getName());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
        ManageClientThread.Offline(message.getSender());
        System.out.println(user.getName() + "退出系统成功");
        System.exit(0);
    }


}

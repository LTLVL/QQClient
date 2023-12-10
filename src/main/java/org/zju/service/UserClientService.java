package Service;

import common.Message;
import common.MessageType;
import common.User;
import utils.Utility;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserClientService {
    private User user = new User();
    private Socket socket = new Socket();


    public boolean Check(String id, String passwd) throws IOException, ClassNotFoundException {
        user.setId(id);
        user.setPasswd(passwd);
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
            ManageClientThread.Add(id, connect);

            b = true;
        } else {
            System.out.println("==========登陆失败==========");
            b = false;
        }
        return b;
    }

    public void OnlineFriend() throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getId());
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getThread(user.getId()).getSocket().getOutputStream());
        oos.writeObject(message);
    }

    public void Exit() throws IOException {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(user.getId());
        message.setGetter(user.getId());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
        ManageClientThread.Offline(message.getSender());
        System.out.println(user.getId()+"退出系统成功");
        System.exit(0);
    }


}

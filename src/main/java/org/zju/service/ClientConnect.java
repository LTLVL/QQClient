package Service;

import common.Message;
import common.MessageType;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
                    String[] s = message.getContent().split(" ");
                    System.out.println("===========当前在线用户列表==========");
                    for (int i = 0; i < s.length; i++) {
                        System.out.println("用户： " + s[i]);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_PRIVATE_MESSAGE)) {
                    System.out.println("收到来自用户 " + message.getSender() + "的私聊消息:");
                    System.out.println(message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    socket.close();
                    break;
                } else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    System.out.println("收到来自用户 " + message.getSender() + "的群发消息:");
                    System.out.println(message.getContent());
                }else if(message.getMesType().equals(MessageType.MESSAGE_CLIENT_FILE)){
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(message.getGetterPath()));
                    bos.write(message.getFileBytes());
                    bos.flush();
                    bos.close();
                    System.out.println("收到来自用户 " + message.getSender() + "的文件:"+message.getSenderPath());
                    System.out.println("保存到路径："+message.getGetterPath());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }
}

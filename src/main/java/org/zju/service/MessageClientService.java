package Service;

import common.Message;
import common.MessageType;
import utils.StreamUtils;
import utils.Utility;

import java.io.*;
import java.util.Date;

public class MessageClientService {
    public static void PrivateChat(String sender) throws IOException {
        System.out.println("请输入想要接收消息的用户");
        String name = Utility.readString(100);
        System.out.println("请输入想要发送的消息：");
        String s = Utility.readString(1000);
        Message message = new Message();
        message.setSender(sender);
        message.setGetter(name);
        message.setContent(s);
        message.setSendTime(new Date().toString());
        System.out.println(sender + " 向 " + name + "发送了一条消息");
        message.setMesType(MessageType.MESSAGE_CLIENT_PRIVATE_MESSAGE);
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getThread(sender).getSocket().getOutputStream());
        oos.writeObject(message);
    }

    public static void CommonChat(String sender) throws IOException {
        System.out.println("请输入想要发送的消息：");
        String s = Utility.readString(1000);
        Message message = new Message();
        message.setSender(sender);
        message.setContent(s);
        message.setSendTime(new Date().toString());
        System.out.println(sender + "群发了一条消息");
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getThread(sender).getSocket().getOutputStream());
        oos.writeObject(message);
    }
    public static void FileTrans(String sender) throws IOException {
        System.out.println("你想将文件发送给谁：");
        String Getter = Utility.readString(100);
        System.out.println("请输入想要发送文件的完整路径：");
        String S1 = Utility.readString(100);
        System.out.println("请输入想要发送文件到对方的路径：");
        String S2 = Utility.readString(100);
        BufferedInputStream bos = new BufferedInputStream(new FileInputStream(S1));
        byte[] bytes = StreamUtils.streamToByteArray(bos);
        Message message = new Message();
        message.setGetter(Getter);
        message.setSender(sender);
        message.setSendTime(new Date().toString());
        message.setMesType(MessageType.MESSAGE_CLIENT_FILE);
        message.setFileBytes(bytes);
        message.setFileLEN(bytes.length);
        message.setSenderPath(S1);
        message.setGetterPath(S2);
        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getThread(sender).getSocket().getOutputStream());
        oos.writeObject(message);
        bos.close();
        System.out.println("用户 " + message.getSender() + "发送文件文件:"+message.getSenderPath()+"给用户"+message.getGetter());
    }
}

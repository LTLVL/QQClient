package QQClient.View;

import Service.MessageClientService;
import Service.UserClientService;
import common.Message;
import common.User;
import utils.Utility;

import java.io.IOException;

public class View {
    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService = new UserClientService();//登陆，注册
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new View().mainMenu();
    }

    private void mainMenu() throws IOException, ClassNotFoundException {
        while (loop) {
            System.out.println("============欢迎登陆QQ=============");
            System.out.println("\t\t 1 登陆QQ");
            System.out.println("\t\t 9 退出系统");

            key = Utility.readString(1);

            switch (key) {
                case "1" -> {
                    System.out.println("请输入用户号");
                    String userid = Utility.readString(50);
                    System.out.println("请输入用户密码");
                    String passwd = Utility.readString(50);
                    User user = new User(userid, passwd);
                    //如果用户合法
                    if (userClientService.Check(userid, passwd)) {
                        System.out.println("============欢迎用户" + userid + "============");
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
                                case "2" -> MessageClientService.CommonChat(userid);
                                case "3" -> MessageClientService.PrivateChat(userid);
                                case "4" -> MessageClientService.FileTrans(userid);
                                case "9" -> {
                                    System.out.println("=============客户端退出系统============");
                                    userClientService.Exit();
                                    loop = false;
                                }
                            }
                        }
                    } else {
                        System.out.println("=============登陆服务器失败============");
                    }
                }
                case "9" -> {
                    System.out.println("=============客户端退出系统============");
                    loop = false;
                }
            }
        }
    }
}

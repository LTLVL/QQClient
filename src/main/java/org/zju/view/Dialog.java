package org.zju.view;


import javax.swing.*;
import java.awt.*;

public class Dialog extends JDialog{

    public Dialog(String msg){
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        this.setBounds(50, 50, 100, 100);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JLabel userLabel = new JLabel(msg);

        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(50, 20, 50, 25);
        this.add(userLabel);
    }
}

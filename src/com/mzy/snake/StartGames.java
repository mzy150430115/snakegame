package com.mzy.snake;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        //1.绘制一个静态窗口
        JFrame  frame = new JFrame("贪吃蛇小游戏");
        //设置界面大小
        frame.setBounds(10,10,900,720);//设置界面大小
        frame.setResizable(false);//窗口大小不可以改变
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置关闭事件

        //2.面板 JPanel
        frame.add(new GamePanel());

        frame.setVisible(true);//让窗口展现出来
    }
}

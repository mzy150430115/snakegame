package com.mzy.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    int length;//蛇的长度
    int[] snakeX = new int[600];
    int[] snakeY = new int[500];
    String fx; //R:右 L：左 U：上 D：下

    boolean isStart = false ;
    boolean isFail = false;
    //积分系统
    int score;

    Timer timer = new Timer(100,this);

    //1.定义一个食物
    int foodx;
    int foody;
    Random random = new Random();

    public void init(){
        length = 3;
        snakeX[0] = 100;snakeY[0] = 100;
        snakeX[1] = 75;snakeY[1] = 100;
        snakeX[2] = 50;snakeY[2] = 100;
        fx = "R";
        score = 0;
        foodx = 25 + 25*random.nextInt(34);
        foody = 75 + 25*random.nextInt(24);
    }
    public GamePanel(){
        init();
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
    }
    //画板：画蛇
    //Graphics 画笔
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //清屏
        this.setBackground(Color.WHITE);
        Data.header.paintIcon(this,g,25,11);
        g.fillRect(25,75,850,600);

        //画静态的小蛇
        if(fx.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("D")) {
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        for(int i=1;i<length;i++){
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        //画积分
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度："+length,750,35);
        g.drawString("分数："+score,750,50);

        Data.food.paintIcon(this,g,foodx,foody);
        if(isStart==false){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }

        //失败提醒
        if(isFail){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏失败，按下空格重新开始",200,300);
        }

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //键盘监听事件
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); //获取按下的键盘

        if (keyCode==KeyEvent.VK_SPACE){ //如果是空格
            if (isFail){ //如果游戏失败,从头再来！
                isFail = false;
                init(); //重新初始化
            }else { //否则，暂停游戏
                isStart = !isStart;
            }
            repaint();
        }

        //键盘控制走向
        if (keyCode==KeyEvent.VK_LEFT){
            fx = "L";
        }else if (keyCode==KeyEvent.VK_RIGHT){
            fx = "R";
        }else if (keyCode==KeyEvent.VK_UP){
            fx = "U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            fx = "D";
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

}
    //定时器，监听时间 帧，执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart && isFail==false){
            for(int i=length-1;i>0;i--){//除了脑袋 身体都像前动
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            if(fx.equals("R")){
                snakeX[0] = snakeX[0]+25;
                if(snakeX[0]>850){
                    snakeX[0] = 25;
                }
            }else if(fx.equals("L")){
                snakeX[0] = snakeX[0]-25;
                if(snakeX[0]<25){
                    snakeX[0] = 850;
                }
            }else if(fx.equals("U")){
                snakeY[0] = snakeY[0]-25;
                if(snakeY[0]<75){
                    snakeY[0] = 650;
                }
            }else if(fx.equals("D")){
                snakeY[0] = snakeY[0]+25;
                if(snakeY[0]>650){
                    snakeY[0] = 75;
                }
            }
            //如果小蛇的头和食物重合
            if(snakeX[0]==foodx && snakeY[0]==foody){
                //长度加1
                length++;
                score = score + 10;

                //重新生成食物
                foodx = 25 + 25*random.nextInt(34);
                foody = 75 + 25*random.nextInt(24);
            }

            //结束判断
            for(int i=1;i<length;i++){
                if(snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                    isFail=true;
                }
            }
            repaint();

        }
        timer.start();
        }
    }

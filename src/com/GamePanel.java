package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//面板
public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //定义蛇的数据结构
    int length;
    int [] snake_x = new int[600];//x坐标
    int [] snake_y = new int[600];//y坐标
    String dir ;
    //游戏当前的状态：开始或者停止
    boolean isStart = false;//默认暂停
    boolean isFail = false;//游戏失败状态
    //食物的坐标
    int food_x;
    int food_y;
    Random random = new Random();
    int score;
    //定时器
    Timer timer = new Timer(100, this);//100ms执行一次定时器

    public GamePanel(){
        init();
        //获得焦点和键盘事件
        this.setFocusable(true);//获得焦点事件
        this.addKeyListener(this);//获得键盘监听事件
        timer.start();
    }
    //初始化
    public void init(){
        length = 3;
        snake_x[0] = 100;snake_y[0] = 100; //脑袋的坐标
        snake_x[1] = 75;snake_y[1] = 100; //第一个身体的坐标
        snake_x[2] = 50;snake_y[2] = 100; //第二个身体的坐标
        dir = "R";
        //让食物位置初始化
        food_x=25 + 25*random.nextInt(34);
        food_y=75 + 25*random.nextInt(24);
        score=0;
    }
    //绘制面板，游戏由这个画笔来画
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        this.setBackground(Color.WHITE);
        //绘制静态面板
        Data.header.paintIcon(this, g, 25, 11);//头部图片
        g.fillRect(25, 75, 850, 600);//默认游戏界面
        //画积分
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度："+length, 750, 35);
        g.drawString("分数："+score, 750, 50);
        //画食物
        Data.food.paintIcon(this, g, food_x, food_y);
        //画小蛇
        if(dir.equals("R")){
            Data.right.paintIcon(this, g, snake_x[0],snake_y[0]);
        }else if(dir.equals("L")){
            Data.left.paintIcon(this, g, snake_x[0],snake_y[0]);
        }if(dir.equals("U")){
            Data.up.paintIcon(this, g, snake_x[0],snake_y[0]);
        }if(dir.equals("D")){
            Data.down.paintIcon(this, g, snake_x[0],snake_y[0]);
        }
        for(int i=1 ; i < length ; i++){
            Data.body.paintIcon(this, g, snake_x[i],snake_y[i]);
        }

        //游戏状态
        if(!isStart){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按下空格开始游戏",300,300);
        }
        if (isFail){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("游戏失败，按下空格重新开始",300,300);
        }
    }
    //键盘监听类
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode==KeyEvent.VK_SPACE){
            if (isFail){
                isFail=false;
                init();
            }else{
                isStart = !isStart;//取反
            }
            repaint();
        }
        if (keyCode==KeyEvent.VK_UP){
            dir="U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            dir="D";
        }else if (keyCode==KeyEvent.VK_LEFT){
            dir="L";
        }else if (keyCode==KeyEvent.VK_RIGHT){
            dir="R";
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    //事件监听--需要
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && !isFail){
            //吃食物
            if (snake_x[0]==food_x&&snake_y[0]==food_y){
                length++;
                //再次随机食物
                score+=10;
                food_x=25 + 25*random.nextInt(34);
                food_y=75 + 25*random.nextInt(24);
            }

            //移动
            for (int i = length-1; i >0;i--){
                snake_x[i]=snake_x[i-1];//向前移动一节
                snake_y[i]=snake_y[i-1];
            }

            if(dir.equals("R")){
                snake_x[0]=snake_x[0]+25;
                if(snake_x[0]>850){ snake_x[0]=25; }
            }else if (dir.equals("L")){
                snake_x[0]=snake_x[0]-25;
                if(snake_x[0]<25){ snake_x[0]=850; }
            }else if (dir.equals("U")){
                snake_y[0]=snake_y[0]-25;
                if(snake_y[0]<75){ snake_y[0]=650; }
            }else if (dir.equals("D")){
                snake_y[0]=snake_y[0]+25;
                if(snake_y[0]>650){ snake_y[0]=75; }
            }

            repaint();
        }
        //失败判断
        for (int i=1;i<length;i++){
            if (snake_x[0]==snake_x[i] && snake_y[0]==snake_y[i]){
                    isFail=true;
            }
        }

        timer.start();//定时器开始
    }
}

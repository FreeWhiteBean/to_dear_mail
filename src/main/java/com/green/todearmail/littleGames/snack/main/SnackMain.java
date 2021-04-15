package com.green.todearmail.littleGames.snack.main;

import com.green.todearmail.littleGames.snack.entity.SnackWin;

import javax.swing.JFrame;

public class SnackMain extends JFrame {
    SnackWin snackwin;
    static final int Width = 800 , Height = 600 , LocX = 200 , LocY = 80;

    public SnackMain() {
        super("GreedySncak_SL");
        snackwin = new SnackWin();
        //在JFrame窗口容器里添加其他组件
        add(snackwin);
        //设置组件的大小
        this.setSize(Width, Height);
        //设置组件的可见性
        this.setVisible(true);
        //设置组件的位置
        this.setLocation(LocX, LocY);
        //snackwin.requestFocus();
    }

    public static void main(String[] args) {
        new SnackMain();
    }
}
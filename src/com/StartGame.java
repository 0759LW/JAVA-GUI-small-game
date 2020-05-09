package com;

import javax.swing.*;
import java.awt.*;

public class StartGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("贪吃蛇");
        frame.setResizable(false);
        frame.setBounds(10, 10, 900, 720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(new GamePanel());
    }
}

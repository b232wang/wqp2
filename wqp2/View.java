package wqp2;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.*;

import java.io.*;

public class View extends JComponent{
    public int h;
    public int w;
    public String[][] pic;
    public int state;
    public int deadState;
    public int level;

    public int widthNum;
    public int heightNum;

    private Controller c;
    public View(int h, int w, Controller c){
        this.h = h;
        this.w = w;
        this.c = c;

        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                c.handleKey(e.getKeyCode());
            }
        });

        state = -1;

    }

    protected void paintComponent(Graphics g){
    }

    public void viewNotify(String[][] pic, int state, int deadState, int level){
        this.pic = pic;
        this.state = state;
        this.deadState = deadState;
        this.level = level;
        repaint();
    }
}

package wqp2;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.*;

import java.io.*;

public class TieView extends View{

    public String dir= "wqp2/data/";

    public int h;
    public int w;

    public WelcomeBoard wb;
    public GameBoard gb;
    public WinPanel wp;
    public DeadPanel dp;
    public PassPanel pp;

    public TieView(int h,int w, Controller c){
        super(h, w, c);

        wb = new WelcomeBoard(0,0,h,w,Color.red);
        gb = new GameBoard(0,0,h,w,Color.red);
        wp = new WinPanel(0,0,h,w,Color.red);
        dp = new DeadPanel(0,0,h,w,Color.red);
        pp = new PassPanel(0,0,h,w,Color.red);

    }

    protected void paintComponent(Graphics g) {
        //int nw = this.getWidth();
        //int nh = this.getHeight();

        switch(state){
            case -1:
                wb.paint(g);
                break;
            case 0:
                gb.pic = pic;
                gb.level = level;
                gb.paint(g, widthNum, heightNum);
                break;
            case 1:
                dp.paint(g);
                break;
            case 2:
                wp.paint(g);
                break;
            case 3:
                pp.paint(g);
                break;

        }

    }

}


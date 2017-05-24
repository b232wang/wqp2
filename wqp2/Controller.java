package wqp2;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

public class Controller{
    public int h;
    public int w;

    public View v;
    public Model m;

    public int state;

    public Controller(int h, int w){
        this.h = h;
        this.w = w;
        v = new TieView(h, w, this);
        m = new Model(this);

        state = -1;

        try{
            JFrame frame = new JFrame();
            frame.setLayout(new GridLayout(1,1));
            frame.setContentPane(v);

            v.setFocusable(true);
            v.requestFocusInWindow();

            frame.setSize(w, h);
            frame.setVisible(true);
        }catch (Exception e){}

    }

    public void notifyXY(int widthNum,int heightNum){
        v.widthNum = widthNum;
        v.heightNum = heightNum;
    }

    public void viewNotify(String[][] pic, int gameState, int deadState, int level){
        state = gameState;
        v.viewNotify(pic, state, deadState, level);

    }

    public void handleKey(int k){
        int ans;
        if(k == KeyEvent.VK_Q){
            System.exit(0);
        }
        switch(state){
            case -1:
                m.initGame();
                break;
            case 0:
                switch(k){
                    case KeyEvent.VK_UP:
                        m.move(0,-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        m.move(0,1);
                        break;
                    case KeyEvent.VK_LEFT:
                        m.move(-1,0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        m.move(1,0);
                        break;
                    case KeyEvent.VK_K:
                        m.move(0,-1);
                        break;
                    case KeyEvent.VK_J:
                        m.move(0,1);
                        break;
                    case KeyEvent.VK_H:
                        m.move(-1,0);
                        break;
                    case KeyEvent.VK_L:
                        m.move(1,0);
                        break;
                    case KeyEvent.VK_R:
                        m.initGame();
                        break;
                }
                break;
            case 1:
                m.initGame();
                break;
            case 5:
                m.nextLevel();
                break;
            case 6:
                System.exit(0);
        }
        v.repaint();

    }

}



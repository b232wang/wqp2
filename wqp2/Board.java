package wqp2;

import java.util.Arrays;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;

public class Board{ int x,y,h,w;
    Color color;
    Color [] colorArray = {Color.pink, Color.red, Color.yellow, Color.blue, Color.black, Color.green, Color.cyan, Color.gray, Color.orange, Color.darkGray, Color.lightGray};
    int cellSize = 50;
    int cellEdge = 2;

    public int id;
    public String msg;

    public String picDir = "wqp2/data/picture/";

    public Board(int x,int y,int h, int w, Color c){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.color = c;
    }
}

//public class TiePanel extends Board{
//    public WelcomeBoard wb;
//    public Question que;
//    public GameBoard gb;
//    public WinPanel wp;
//    public DeadPanel dp;
//    public PassPanel pp;
//    public GenderPanel gp;
//
//    int state;
//    int level;
//
//    public TiePanel(int h, int w, Wqp2 w2){
//        super(0,0,h,w,Color.white);
//        double edgeRate = 0.02;
//        double botRate = 0.20;
//        wb = new WelcomeBoard(0,0,h,w, colorArray[0]);
//        que = new Question((int)(edgeRate * w), (int)(edgeRate * h), (int)(botRate *(1-4*edgeRate)* h), (int)((1-2*edgeRate) * w), colorArray[1]);
//        gb = new GameBoard((int)(edgeRate * w), (int)(botRate *(1-4*edgeRate) * h + 2 * edgeRate * h), (int)((1-botRate)*(1-4*edgeRate)* h), (int)((1-2*edgeRate) * w),colorArray[9], this);
//
//        dp = new DeadPanel(0,0,h,w, colorArray[8]);
//        wp = new WinPanel(0,0,h,w, colorArray[8]);
//        pp = new PassPanel(0,0,h,w, colorArray[8]);
//        gp = new GenderPanel(0,0,h,w,colorArray[8]);
//
//    }
//
//    public void paint(Graphics g){
//        switch(state){
//            case 0:
//                wb.paint(g);
//                break;
//            case 10:
//                gp.paint(g);
//                break;
//            case 1:
//                que.level = level;
//                que.paint(g);
//                gb.paint(g);
//                break;
//            case 2:
//                dp.msg = waMsg;
//                dp.id = 2;
//                dp.paint(g);
//                break;
//            case 3:
//                dp.msg = "You meet Dead Loop";
//                dp.id = 1;
//                dp.paint(g);
//                break;
//            case 4:
//                dp.msg = "You meet a break";
//                dp.id = 0;
//                dp.paint(g);
//                break;
//            case 5:
//                wp.paint(g);
//                break;
//            case 6:
//                pp.paint(g);
//                break;
//        }
//    }
//
//    public void viewNotify(){
//    }
//}

class WelcomeBoard extends Board{
    public WelcomeBoard(int x,int y ,int h,int w, Color c){
        super(x, y, h, w, c);
    }

    public void paint(Graphics g){
        // insert image
        BufferedImage image;
        Image scaledImage ;
        try {
            String picName = picDir + "title.png";
            image = ImageIO.read(new File(picName));
            scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            g.drawImage(scaledImage, x, y, null);
        } catch (IOException ex) {}
    }
}

class GameBoard extends Board{
    public String[][] pic;
    public int level;


    public GameBoard(int x,int y,int h, int w, Color c){
        super(x,y,h,w,c);
    }

    public void paint(Graphics g,int width, int height){
        // insert image
        BufferedImage image;
        Image scaledImage ;
        try {
            String picName = picDir + "background.png";
            image = ImageIO.read(new File(picName));
            scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            g.drawImage(scaledImage, x, y, null);
        } catch (IOException ex) {}

        g.setFont(new Font("TimesRoman", Font.BOLD, 13));

        int numX = width;
        int numY = height;
        int sx = (w - cellSize * numX + cellEdge * (numX - 1)) / 2 + x;
        int sy = (h - cellSize * numY + cellEdge * (numY - 1)) / 2 + y;
        for(int i = 0; i < numY; i++){
            for(int j = 0;j < numX; j++){
                int ix = sx + j*(cellSize + cellEdge);
                int iy = sy + i * (cellSize + cellEdge);
                try {
                    String picName = picDir + pic[i][j];
                    image = ImageIO.read(new File(picName));
                    scaledImage = image.getScaledInstance(cellSize, cellSize, Image.SCALE_DEFAULT);
                    g.drawImage(scaledImage, ix,iy, null);
                } catch (IOException ex) {}
            }
        }
    }
}

class DeadPanel extends Board{

    public DeadPanel(int x,int y,int h, int w, Color c){
        super(x,y,h,w,c);
    }

    public void paint(Graphics g){
        //g.setColor(color);
        //g.fillRect(x,y,w,h);
        //g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        //g.setColor(Color.black);
        //g.drawString("You Dead!", x + w/2,  y + h /2);
        //g.setFont(new Font("TimesRoman", Font.BOLD, 13));
        //g.setColor(Color.black);
        //g.drawString(msg, x + w/2,  y + h /2 + 40);
        //g.setFont(new Font("TimesRoman", Font.BOLD, 13));
        //g.setColor(Color.black);
        //g.drawString("press any key to restart(except Q)", x + w/2,  y + h /2 + 80);

        // insert image
        BufferedImage image;
        Image scaledImage ;
        try {
            String picName = picDir + "lose_back.png";
            image = ImageIO.read(new File(picName));
            scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            g.drawImage(scaledImage, x, y, null);
            //if(id == 2){
            //    int theId = Integer.parseInt(msg.split(";b;r;k;")[0]);
            //    String theMsg = msg.split(";b;r;k;")[1];

            //    g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            //    g.setColor(Color.black);
            //    g.drawString("Your answer is: " + ansArr[theId], x + 100,  y + 80);
            //    g.setColor(Color.red);
            //    g.drawString(theMsg, x + w/2 - 400,  y + h /2 );
            //}
            //if(id == 0){
            //    g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            //    g.setColor(Color.red);
            //    g.drawString("You hit the dagger!", x + w/2 - 400,  y + h /2 );
            //}
            //if(id == 1){
            //    g.setFont(new Font("TimesRoman", Font.BOLD, 30));
            //    g.setColor(Color.red);
            //    g.drawString("You fall to an infinite loop!", x + w/2 - 400,  y + h /2 );
            //}
        } catch (IOException ex) {}
    }
}

class WinPanel extends Board{

    public WinPanel(int x,int y,int h, int w, Color c){
        super(x,y,h,w,c);
    }

    public void paint(Graphics g){
        // insert image
        BufferedImage image;
        Image scaledImage ;
        try {
            String picName = picDir + "win_back.png";
            image = ImageIO.read(new File(picName));
            scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            g.drawImage(scaledImage, x, y, null);
        } catch (IOException ex) {}
    }
}

class PassPanel extends Board{

    public PassPanel(int x,int y,int h, int w, Color c){
        super(x,y,h,w,c);
    }

    public void paint(Graphics g){
        //g.setColor(color);
        //g.fillRect(x,y,w,h);
        //g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        //g.setColor(Color.black);
        //g.drawString("You Pass the all level!", x + w/2,  y + h /2);

        // insert image
        BufferedImage image;
        Image scaledImage ;
        try {
            String picName = picDir + "clear.png";
            image = ImageIO.read(new File(picName));
            scaledImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            g.drawImage(scaledImage, x, y, null);
        } catch (IOException ex) {}
    }
}


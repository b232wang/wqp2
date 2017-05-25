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
                    if(pic[i][j] == "male.png"){
                        System.out.println("view player");
                        System.out.println(i);
                        System.out.println(j);
                    }
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


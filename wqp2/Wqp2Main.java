package wqp2;

import javax.swing.*;
import java.awt.*;

public class Wqp2Main{

    public static void main(String args[]) {
        Controller c = new Controller(800, 1200);
        //int h = 800;
        //int w = 1200;
        //try{
        //    Wqp2 wq = new Wqp2(h,w);

        //    JFrame frame = new JFrame();
        //    frame.setLayout(new GridLayout(1,1));
        //    frame.setContentPane(wq);

        //    wq.setFocusable(true);
        //    wq.requestFocusInWindow();

        //    frame.setSize(w, h);
        //    frame.setVisible(true);

        //} catch (Exception e){
        //    System.out.println("exception error");
        //}
    }

    //public static void main2(String args[]) throws Exception{
    //    View v = new View();
    //    Model m = new Model(v);
    //    // m.view = v;
    //    // v.model = m;
    //    int ch;
    //    System.out.print("enter: ");
    //    while ((ch = System.in.read()) != -1){
    //        int x = 0;
    //        int y = 0;
    //        if (ch != '\n' && ch != '\r'){
    //            char c = (char)ch;
    //            if(c == 'h'){
    //                x = -1;
    //            }
    //            if(c == 'l'){
    //                x = 1;
    //            }
    //            if(c == 'j'){
    //                y = 1;
    //            }
    //            if(c == 'k'){
    //                y = -1;
    //            }
    //            m.move(x, y);
    //            System.out.print("enter: ");
    //        }

    //    }

    //}
}

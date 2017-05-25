package wqp2;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.util.Random;



public class Model {
    public int width;
    public int height;
    public int numberLeft;

    private Controller c;

    public int level;
    public int totLvl;

    // gmaeState present:
    // 0 game is going
    // 1 game lose (dead)
    // 2 game win
    // 3 pass all level
    public int gameState;

    // deadState present:
    // 0 not dead
    // 1 dead with wheel
    // 2 dead with infinite loop
    // 3 dead with monster
    public int deadState;

    // mode present:
    // 0 normal mode
    public int mode;
    public String modeName;

    public String modeDir = "wqp2/data/";

    public Cell[][] board;
    public Player player;

    public Model(Controller c){
        mode = 0;
        this.c = c;
        modeName = "mode0";
        initMode();
    }

    public Model(Controller c, int mode, String modeName){
        this.mode = mode;
        this.modeName = modeName;
        this.c = c;
        initMode();
    }


    public void initMode(){
        switch(mode){
            case 0:
                modeDir = "wqp2/data/mode0/";
                level = 0;
                readInfo();
                break;
        }
    }

    public void nextLevel(){
        level++;
        if(level >= totLvl){
            gameState = 3;
            viewNotify();
            return;
        }
        initGame();
    }

    private void readInfo(){
        BufferedReader br = null;
        FileReader fr = null;

        String fileName = modeDir+"info.txt";
        try{
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;
            br = new BufferedReader(new FileReader(fileName));
            line = br.readLine();

            totLvl = Integer.parseInt(line);
        }catch(Exception e){}
    }

    public void initGame(){
        readMap(modeDir + "map" + Integer.toString(level) + ".txt");
        notifyInit();
        gameState = 0;
        viewNotify();
    }

    public void notifyInit(){
        c.notifyXY(width, height);
    }


    public void readMap(String fileName){
        BufferedReader br = null;
        FileReader fr = null;

        try{
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;
            br = new BufferedReader(new FileReader(fileName));

            line = br.readLine();
            String[] strArr = line.split(" ");
            this.width = Integer.parseInt(strArr[0]);
            this.height= Integer.parseInt(strArr[1]);
            this.numberLeft= Integer.parseInt(strArr[2]);

            board = new Cell[height][width];
            int i = 0;
            while ((line = br.readLine()) != null) {
                int len = line.length();
                for(int j = 0;j < width;j++){
                    char c = line.charAt(j);
                    board[i][j] = genCell(c, i, j);
                }
                i++;
            }

        }catch (IOException e) {}
    }

    public void viewNotify(){
        String[][] str2D = new String[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                str2D[i][j] = board[i][j].pic;
            }
        }
        c.viewNotify(str2D, gameState, deadState, level);

    }

    public Cell genCell(char c, int y, int x){
        Cell temp = null;
        switch(c){
            case '@':
                player = new Player(x,y,0);
                temp = player;
                break;
            case 'A':
                temp = new Wall(x,y,1);
                break;
            case '+':
                temp = new Point(x,y,2);
                break;
            case '*':
                temp = new Wheel(x,y,2);
                break;
            case '1':
                temp = new Monster(x,y,3,'1',0,1);
                break;
            case '2':
                temp = new Monster(x,y,4,'2',1,0);
                break;
            case '3':
                temp = new Monster(x,y,5,'3',-1,0);
                break;
            case '4':
                temp = new Monster(x,y,6,'4',0,-1);
                break;
            case '?':
                temp = new GreenPoint(x,y,7);
                break;
            case '&':
                GreenPoint temp2 = new GreenPoint(x,y,1);
                temp2.name = '&';
                temp2.passAble = 0;
                temp2.color = 8;
                temp = (Cell)temp2;
                break;
            case '.':
                temp = new Empty(x,y);
                break;
        }
        return temp;
    }

    public boolean moveTo(int nx,int ny, int mx, int my){
        int code = board[ny][nx].passIt(mx, my);

        System.out.println("moveto");

        if(code == 0) return false;

        if(code == -1){
            //dead with monster
            System.out.println(ny);
            System.out.println(nx);
            System.out.println("test2");
            gameState = 1;
            deadState = 3;
            return false;
        }
        if(code == -2){
            //dead with wheel

            System.out.println("test3");
            gameState = 1;
            deadState = 1;
            return false;
        }
        if(code == 2){
            //pass a right answer
            //got point
            getPoint();
            board[ny][nx] = new Empty(nx, ny);
        }

        int ox = player.x;
        int oy = player.y;
        player.setPos(nx, ny);

        board[oy][ox] = player.preCell;
        player.preCell = board[ny][nx];
        board[ny][nx] = player;

        if(isWin()){
            gameState = 2;
            gameWin();
            return false;
        }

        return true;
    }

    public void getPoint(){
        numberLeft--;
    }

    public boolean isWin(){
        return numberLeft == 0;
    }

    public void move(int mx, int my){
        int initX = player.x;
        int initY = player.y;
        int nx, ny;
        boolean rv = true;
        int passTime = 0;
        while(rv){
            nx = player.x + mx;
            ny = player.y + my;

            if(nx == -1){
                nx = width-1;
            }
            if(nx == width){
                nx = 0;
            }
            if(ny == -1){
                ny = height-1;
            }
            if(ny == height){
                ny = 0;
            }

            //add new
            rv = moveTo(nx, ny, mx, my);
            System.out.println("move x y");
            System.out.println(player.y);
            System.out.println(player.x);
            System.out.println(ny);
            System.out.println(nx);

            if(rv && player.x == initX && player.y == initY){
                if( passTime == 3 ){
                    System.out.println("test1");
                    gameState = 1;
                    deadState = 2;
                    rv = false;
                }else{
                    passTime++;
                }
            }

            viewNotify();
        }
    }

    public void gameWin(){
        //TODO
    }

}

class Cell {
    public int x;
    public int y;
    public int color;
    public char name;

    public String pic;

    public int id = -1;
    public String msg = "empty";

    public Cell(int x,int y, int color,char name){
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = name;
    }

    public int passIt(int mx, int my){
        // code present:
        // 2 got point and pass
        // 1 passable
        // 0 cannot pass
        // -1 Dead with monster
        // -2 Dead with wheel
        return 1;
    }

    public void setPos(int x ,int y){
        this.x = x;
        this.y = y;
    }
}

class Empty extends Cell {

    public Empty(int x,int y){
        super(x, y, 0, '.');
        pic = null;
    }

    public int passIt(int mx, int my){
        return 1;
    }
}

class Player extends Cell {

    public Cell preCell;

    public Player(int x,int y, int color){
        super(x,y,color,'@');
        this.preCell = new Empty(x, y);
        pic = "male.png";
    }

    public int passIt(int mx, int my){
        return 0;
    }
}

class Wall extends Cell {

    public Wall(int x,int y, int color){
        super(x,y,color,'A');
        pic = "wall1.png";
    }

    public int passIt(int mx, int my){
        return 0;
    }
}

class Point extends Cell {

    public Point(int x,int y, int color){
        super(x,y,color,'+');
        pic = "A.png";
    }

    public int passIt(int mx, int my){
        return 2;
    }
}

class Wheel extends Cell {

    public Wheel(int x,int y, int color){
        super(x,y,color,'*');
        pic = "B.png";
    }

    public int passIt(int mx, int my){
        return -2;
    }
}

class Monster extends Cell{
    public int dx = -1;
    public int dy;


    public Monster(int x, int y, int color, char name, int dx, int dy){
        super(x,y,color,name);
        this.dx = dx;
        this.dy = dy;
        int ttnum = dx - 2 * dy;
        switch(ttnum){
            case 1:
                pic = "left.png";
                break;
            case -1:
                pic = "right.png";
                break;
            case 2:
                pic = "down.png";
                break;
            case -2:
                pic = "up.png";
                break;
        }
    }

    public int passIt(int mx, int my){
        if(mx == dx && my == dy){
            return -1;
        }
        return 0;
    }
}

class GreenPoint extends Cell{
    int passAble = 1;
    char greenWall = '&';
    String before = "seed.png";
    String after = "tree.png";

    public GreenPoint (int x, int y, int color){
        super(x,y,color,'?');
        pic = before;
    }

    public int passIt(int mx, int my){
        if(passAble == 1){
            passAble = 0;
            name = greenWall;
            pic = after;
            return 1;
        }
        return 0;
    }
}

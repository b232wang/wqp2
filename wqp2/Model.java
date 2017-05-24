package wqp2;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.util.Random;



public class Model {
    public int width;
    public int height;
    public int numberLeft;
    public int numAns;
    public String[] ansArr;

    // gmaeState present:
    // 0 game is going
    // 1 game lose (dead)
    // 2 game win (dead)
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

    public String msg;
    public String QuestionLine = "empty";
    public String dir = "wqp2/data/";

    public View view;

    public Cell[][] board;
    public Player player;

    public Model(View view, int gender, int level){
        this.view = view;
        view.model = this;
        mode = 0;
        initMode();
        initGame();
        //heForSheMode(gender,level);
    }

    public void initMode(){

    }

    public void initGame(){

    }


    public void initModel1(String fileName){
        BufferedReader br = null;
        FileReader fr = null;


        try{
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;
            br = new BufferedReader(new FileReader(fileName));

            line = br.readLine();
            this.width = Integer.parseInt(line.split(" ")[0]);
            this.height= Integer.parseInt(line.split(" ")[1]);
            this.numberLeft= Integer.parseInt(line.split(" ")[2]);
            this.numAns = numberLeft + Integer.parseInt(line.split(" ")[3]);

            board = new Cell[height][width];
            int i = 0;
            while ((line = br.readLine()) != null) {
                int len = line.length();
                for(int j = 0;j < width;j++){
                    //char c = (char)(line[j]);
                    char c = line.charAt(j);
                    board[i][j] = genCell(c, i, j);
                    //if(board[i][j] != null) {
                    //}
                }
                i++;
            }
            view.viewNotify();

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
                temp = new RAns(x,y,2);
                break;
            case '*':
                temp = new WAns(x,y,2);
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
        }
        return temp;
    }

    public boolean moveTo(int nx,int ny){
        int code = board[ny][nx].passIt(mx, my);

        if(code == -1){
            //dead with monster
            gameState = 1;
            deadState = 3;
            //view.viewNotify();
            return false;
        }
        if(code == -2){
            //dead with wheel

            gameState = 1;
            deadState = 1;
            return false;
        }
        if(code == 2){
            //pass a right answer
            //got point
            getPoint();
            board[ny][nx] = new Empty(nx, ny);
            if(isWin()){
                gameState = 5;
                gameWin();
            }
        }

        int ox = player.x;
        int oy = player.y;
        player.setPos(nx, ny);

        board[oy][ox] = player.preCell;
        player.preCell = board[ny][nx];
        board[ny][nx] = player;

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
            rv = moveTo(nx,ny);

            if(rv && player.x == initX && player.y == initY){
                gameState = 1;
                deadState = 2;
            }

            view.viewNotify();
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
        return 0;
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

class RAns extends Cell {

    public RAns(int x,int y, int color){
        super(x,y,color,'+');
    }

    public int passIt(int mx, int my){
        return 2;
    }
}

class WAns extends Cell {

    public WAns(int x,int y, int color){
        super(x,y,color,'*');
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
    String pic1 = "seed.png";
    String pic2 = "tree.png";

    public GreenPoint (int x, int y, int color){
        super(x,y,color,'?');
        pic = "seed.png";
    }

    public int passIt(int mx, int my){
        if(passAble == 1){
            passAble = 0;
            name = greenWall;
            pic = pic2;
            return 1;
        }
        return 0;
    }
}

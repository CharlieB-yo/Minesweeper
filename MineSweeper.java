import java.util.Scanner;

public class MineSweeper{
    Mine[][] mines = new Mine[9][18];
    Scanner in = new Scanner (System.in);
    boolean pop = false;

    public void populate(int numMines){
        int i = 0;
        int rand = 0;
        while(i < numMines){
            rand = (int)(Math.random() * mines.length * mines[0].length);
            if(!mines[rand/mines[0].length][rand % mines[0].length].clicked &&!mines[rand/mines[0].length][rand%mines[0].length].bomb){
                mines[rand/mines[0].length][rand%mines[0].length].bomb = true;
                i++;
            }
        }
        pop = true;
    }
public MineSweeper(){
    for(int r = 0; r < mines.length; r++){
        for(int c = 0; c < mines[0].length; c++){
            mines[r][c] = new Mine(false);
        }
    }
}
public void clicker(int x, int y){
    if(mines[x][y].bomb){System.out.println("BOOM"); System.exit(0); }
    if(pop && x<mines.length && x>=0 && y>=0 && y<mines[0].length){
        mines[x][y].click();
        if(bombsNear(x,y) == 0){
            for(int r = x-1; r <= x+1 && r < mines.length; r++){
                for(int c = y-1; c <= y+1 && c < mines[0].length; c++){
                    if(r<0)r++;
                    if(c<0)c++;
                    if(r>mines.length)r--;
                    if(c>mines[0].length)c--;
                    if(!mines[r][c].clicked){
                        clicker(r,c);
                    }
               }
            }
        }
    }
    else if(!pop){
        mines[x][y].click();
        populate(25);
        clicker(x, y);
    }
}
public boolean awaitClick(){
    System.out.println("please write a command ex: \"g5 click\" or \"b0 flag\"");
	String command = in.nextLine();
    if(command.length()<4) return false;
    int row = Integer.valueOf(command.substring(1, 2));
    int col = (int)(command.charAt(0))-97;
    if(row<mines.length && row>=0 && col>=0 && col<mines[0].length && !mines[row][col].clicked){
        if(command.substring(3).equals("click")){
            clicker(row, col);
            return true;
        }
        else if(command.substring(3).equals("flag")){
            mines[row][col].flag();
            return true;
        }
    }
    return false;
}
public int bombsNear(int x, int y){
    int out = 0;
    for(int r = x-1; r <= x+1 && r < mines.length; r++){
        for(int c = y-1; c <= y+1 && c < mines[0].length; c++){
            if(r<0)r++;
            if(c<0)c++;
            if(r>=mines.length)r--;
            if(c>=mines[0].length)c--;
            if(mines[r][c].bomb){
                out++;
            }
        }
    }
    return out;
}

public boolean clickedNear(int x, int y){
    for(int r = x-1; r <= x+1 && r < mines.length; r++){
        for(int c = y-1; c <= y+1 && c < mines[0].length; c++){
            if(r<0)r++;
            if(c<0)c++;
            if(mines[r][c].clicked){
                return true;
            }
        }
    }
    return false;
}

public char toChar(int x, int y){
    if(mines[x][y].clicked){
        if(clickedNear(x,y)){
            return (char)(bombsNear(x, y)+48);
       }
       else{
        //return '□';
        return 'c';
       }
    }
    else if(mines[x][y].flagged){
        //return '⚑';
        return 'f';
    }
    else{
        //return '■';
        return 'e';
    }
}

public void print(){
    System.out.print("  ");
    for(int i = 0; i < mines[0].length; i++){
        System.out.print((char)(i+97) + " ");
    }
    System.out.println();
    for(int r = 0; r < mines.length; r++){
        System.out.print(r+" ");
        for(int c = 0; c < mines[0].length; c++){
            System.out.print(toChar(r,c)+" ");
        }
        System.out.println();
    }
}

public static void main(String[] args) {
    MineSweeper ms = new MineSweeper();
    while(true){
        ms.print();
        while(!ms.awaitClick());
    }

}
    
}

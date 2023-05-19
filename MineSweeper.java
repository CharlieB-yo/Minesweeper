import java.util.Scanner;

public class MineSweeper{
    Mine[][] mines = new Mine[9][18];
    Scanner in = new Scanner (System.in);
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
    }
public MineSweeper(){
    for(int r = 0; r < mines.length; r++){
        for(int c = 0; c < mines[0].length; c++){
            mines[r][c] = new Mine(false);
        }
    }
}
public boolean awaitClick(){
    System.out.println("please write a command ex: \"1g click\" or \"0b flag\"");
	String command = in.nextLine();
    int row = Integer.valueOf(command.substring(0, 1));
    int col = (int)(command.charAt(1))-97;
    if(row<mines.length && row>=0 && col>=0 && col<mines[0].length && !mines[row][col].clicked){
        if(command.substring(3).equals("click")){
            mines[row][col].click();
            if(bombsNear(row,col) == 0){
                for(int r = row-1; r <= row+1 && r < mines.length; r++){
                    for(int c = col-1; c <= col+1 && c < mines[0].length; c++){
                        if(r<0)r++;
                        if(c<0)c++;
                        if(r>=mines.length)r--;
                        if(c>=mines[0].length)c--;
                        if(!mines[r][c].clicked){
                            mines[r][c].click();
                        }
                    }
                }
            }
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
        return '□';
    }
    else if(mines[x][y].flagged){
        return '⚑';
    }
     else if(clickedNear(x,y)){
         return (char)(bombsNear(x, y)+48);
    }
    else{
        return '■';
    }
}

public void print(){
    for(int r = 0; r < mines.length; r++){
        for(int c = 0; c < mines[0].length; c++){
            System.out.print(toChar(r,c)+" ");
        }
        System.out.println();
    }
}

public static void main(String[] args) {
    MineSweeper ms = new MineSweeper();
    ms.print();
    while(!ms.awaitClick());
    ms.populate(25);
    while(true){
        ms.print();
        while(!ms.awaitClick());
    }

}
    
}

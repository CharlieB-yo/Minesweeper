public class MineSweeper{
    Mine[][] mines = new Mine[16][16];

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

    
}

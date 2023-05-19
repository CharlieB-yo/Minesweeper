public class Mine{
    public boolean bomb;
    public boolean flagged;
    public boolean clicked;
    public Mine(boolean b){
        this.bomb = b;
        this.flagged = false;
        this.clicked = false;
    }
    public void click(){
        clicked = true;
    }
    public void flag(){
        flagged = true;
    }
}

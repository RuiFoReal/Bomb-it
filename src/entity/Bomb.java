package entity;

public class Bomb extends Entity{
    private int bomb_time;
    public Bomb(int x,int y) {
        setX(x);
        setY(y);
        setBomb_time(150);
    }

    public void RemoveBombTime(){
        setBomb_time(getBomb_time()-1);
    }

    public int getBomb_time() {
        return bomb_time;
    }

    public void setBomb_time(int bomb_time) {
        this.bomb_time = bomb_time;
    }
}

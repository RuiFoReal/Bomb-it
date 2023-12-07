package entity;

import java.util.ArrayList;

public class Player extends Entity implements Cloneable{

    private ArrayList<Bomb> bombs=new ArrayList<>();
    private int cooldown_time=0;
    Toward toward= Toward.right;
    public Player(int x,int y){
        setHealth(100);
        setX(x);
        setY(y);
    }
    public void SummonBomb(){
        setCooldown_time(150);
        bombs.add(new Bomb(this.getX(),this.getY()));
    }

    public void Move(Toward toward){
        if(toward==null)return;
        this.setToward(toward);
        switch (toward){
            case up -> this.setY(this.getY() - 2);
            case down -> this.setY(this.getY() + 2);
            case left ->this.setX(this.getX() - 2);
            case right -> this.setX(this.getX() + 2);
        }
    }

    public void RemoveCooldownTime(){
        setCooldown_time(getCooldown_time()-1);
    }
    public void RemoveHealth(int hurt){
        this.setHealth(this.getHealth()-hurt);
    }
    public Toward getToward() {
        return toward;
    }

    public void setToward(Toward toward) {
        this.toward = toward;
    }

    public int getCooldown_time() {
        return cooldown_time;
    }

    public void setCooldown_time(int cooldown_time) {
        this.cooldown_time = cooldown_time;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Player player=new Player(this.getX(),this.getY());
        player.setHealth(this.getHealth());
        player.setCooldown_time(this.getCooldown_time());
        player.setToward(this.toward);
        if(bombs != null) bombs = (ArrayList<Bomb>) this.bombs.clone();
        return player;
    }
}
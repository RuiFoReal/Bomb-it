package entity;

public class Wall extends Entity{
    private WallType walltype;
    public Wall(int x,int y,WallType wallType){
        setHealth(1);
        setX(x);
        setY(y);
        setWalltype(wallType);
    }

    public WallType getWalltype() {
        return walltype;
    }

    public void setWalltype(WallType walltype) {
        this.walltype = walltype;
    }
}

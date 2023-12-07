package gui;

import entity.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import jmp123.PlayBack;

class AsyncThread extends Thread {
    @Override
    public void run() {
        PlayBack playBack = new PlayBack(new jmp123.output.Audio());
        try {
            playBack.open("E:\\Bomb It\\resource\\sounds\\exp.mp3", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        playBack.start(true);
    }
}

public class Map extends JPanel {

    static ArrayList<Wall> walls = new ArrayList<>();
    static ArrayList<Player> players = new ArrayList<>();
    static Player mainplayer = new Player(0, 0);
    static Player player1=new Player(180,180);
    static Player player2 = new Player(270, 270);
    static Player player3=new Player(90,90);
    static Image backimage;

    public Map(){
        players.add(mainplayer);
        players.add(player2);
        players.add(player3);
        players.add(player1);
        //x-[0,540] y-[0,510]
        for (int i = 0; i <= 540; i += 30) {
            for (int j = 0; j <= 510; j += 30) {
                if (!((i == 0 && j == 0) || (i == 270 && j == 270))) {
                    Random random = new Random();
                    if (random.nextInt(50) > 25) {
                        switch (random.nextInt(7)) {
                            case 1 -> walls.add(new Wall(i, j, WallType.cap));
                            case 2 -> walls.add(new Wall(i, j, WallType.wall2));
                            case 3 -> walls.add(new Wall(i, j, WallType.wall1));
                            case 4 -> walls.add(new Wall(i, j, WallType.lubiao2));
                            case 5 -> walls.add(new Wall(i, j, WallType.lubiao1));
                            case 6 -> walls.add(new Wall(i, j, WallType.tree));
                        }
                    }
                }
            }
        }
    }

    public void paint(Graphics g){
        Player temp;
        try {
            temp = (Player) mainplayer.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(mainplayer.getX() + "," + mainplayer.getY());
        if (backimage == null) {
            backimage = this.createImage(600, 600);
            Graphics graphics = backimage.getGraphics();
            for (int x = 0; x <= 570; x += 30) {
                for (int y = 0; y <= 570; y += 30) {
                    graphics.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\back.png").getImage(), x, y, null);
                }
            }
        }

        g.drawImage(backimage, 0, 0, null);
        super.paint(g);

        for (Player player : players) {
            g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\" + player.getToward() + ".png").getImage(), player.getX() - 10, player.getY() - 10, null);
            g.drawRect(player.getX(), player.getY(), 20, 20);
        }
        g.drawString(".", mainplayer.getX(), mainplayer.getY());
        for (Wall wall : walls) {
            g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\" + wall.getWalltype() + ".png").getImage(), wall.getX() - 2, wall.getY() - 15, null);
            g.drawRect(wall.getX(), wall.getY(), 30, 30);
            g.drawString(".", wall.getX(), wall.getY());
        }
        mainplayer.Move(MainJFrame.playertoward);
        if (MainJFrame.bomb) {
            MainJFrame.bomb = false;
            if (mainplayer.getCooldown_time() == 0) mainplayer.SummonBomb();
        }
        if (mainplayer.getCooldown_time() > 0) {
            mainplayer.RemoveCooldownTime();
        }
        for (Wall wall : walls) {
            if (isOverlap(mainplayer.getX(), mainplayer.getY(), wall.getX(), wall.getY())) {
                mainplayer.setX(temp.getX());
                mainplayer.setY(temp.getY());
            }
        }


        for (Player p : players) {
            for (Bomb b:p.getBombs()) {

                b.RemoveBombTime();
                if (b.getBomb_time() == 0) {
                    new AsyncThread().start();
                    for (Player player : players) {
                        if (isOverlap(b.getX() + 30, b.getY(), player.getX(), player.getY())) {
                            player.RemoveHealth(50);
                        } else if (isOverlap(b.getX() - 30, b.getY(), player.getX(), player.getY())) {
                            player.RemoveHealth(50);
                        } else if (isOverlap(b.getX(), b.getY() + 30, player.getX(), player.getY())) {
                            player.RemoveHealth(50);
                        } else if (isOverlap(b.getX(), b.getY() - 30, player.getX(), player.getY())) {
                            player.RemoveHealth(50);
                        } else if (isOverlap(b.getX(), b.getY(), player.getX(), player.getY())) {
                            player.RemoveHealth(50);
                        }
                    }
                    for (Wall wall : walls) {
                        if (wall.getWalltype() != WallType.wall1 && wall.getWalltype() != WallType.wall2) {
                            if (isOverlap(b.getX() + 30, b.getY(), wall.getX(), wall.getY())) {
                                wall.setHealth(0);
                            } else if (isOverlap(b.getX() - 30, b.getY(), wall.getX(), wall.getY())) {
                                wall.setHealth(0);
                            } else if (isOverlap(b.getX(), b.getY() + 30, wall.getX(), wall.getY())) {
                                wall.setHealth(0);
                            } else if (isOverlap(b.getX(), b.getY() - 30, wall.getX(), wall.getY())) {
                                wall.setHealth(0);
                            }
                        }
                    }
                }
                if (b.getBomb_time() >= 10) {
                    g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\bomb.png").getImage(), b.getX() - 5, b.getY() - 5, null);
                    g.drawRect(b.getX(), b.getY(), 20, 20);
                } else {
                    g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\wave1.png").getImage(), b.getX(), b.getY(), null);
                    g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\wave2right.png").getImage(), b.getX() + 30, b.getY(), null);
                    g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\wave2left.png").getImage(), b.getX() - 30, b.getY(), null);
                    g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\wave2down.png").getImage(), b.getX(), b.getY() + 30, null);
                    g.drawImage(new ImageIcon("E:\\Bomb It\\resource\\picture\\wave2up.png").getImage(), b.getX(), b.getY() - 30, null);
                }
            }
        }


        for (Player p : players) {
            for (int i = 0; i < p.getBombs().size(); i++) {
                if(p.getBombs().get(i).getBomb_time()==0){
                    p.getBombs().remove(p.getBombs().get(i));
                    i--;
                }
            }
        }
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).getHealth() == 0) {
                walls.remove(walls.get(i));
                i--;
            }
        }
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getHealth());
            if (players.get(i).getX() +20> 600 || players.get(i).getX() < 0 || players.get(i).getY() +30> 600 || players.get(i).getY() < 0) {
                mainplayer.setX(temp.getX());
                mainplayer.setY(temp.getY());
            }
            if (players.get(i).getHealth() == 0) {
                players.remove(players.get(i));
                i--;
            }
        }
    }

    public static boolean isOverlap(int x1, int y1, int x2, int y2) {
        return x1 + 20 > x2 &&
                x2 + 30 > x1 &&
                y1 + 20 > y2 &&
                y2 + 30 > y1;
    }
}

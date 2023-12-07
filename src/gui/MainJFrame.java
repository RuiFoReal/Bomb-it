package gui;

import entity.Toward;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Thread.sleep;

public class MainJFrame extends JFrame implements KeyListener {
    static MainJFrame mainJFrame;
    static Toward playertoward=null;
    static boolean bomb = false;


    static Map map = new Map();

    public MainJFrame(String title) {
        setTitle(title);
        setSize(600, 600);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            playertoward = Toward.up;
        }else if (e.getKeyCode() == KeyEvent.VK_S) {
            playertoward = Toward.down;
        }else if (e.getKeyCode() == KeyEvent.VK_A) {
            playertoward = Toward.left;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            playertoward = Toward.right;
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bomb = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_W) {
//            playertoward=null;
//        } else if (e.getKeyCode() == KeyEvent.VK_S) {
//            playertoward=null;
//        } else if (e.getKeyCode() == KeyEvent.VK_A) {
//            playertoward=null;
//        } else if (e.getKeyCode() == KeyEvent.VK_D) {
//            playertoward=null;
//        }

//        if(e.getKeyCode() != KeyEvent.VK_W&&e.getKeyCode() != KeyEvent.VK_A&&e.getKeyCode() != KeyEvent.VK_S&&e.getKeyCode() != KeyEvent.VK_D)return;
//        playertoward=null;
    }


    public static void main(String[] args) throws InterruptedException {
        EventQueue.invokeLater(() -> {
            mainJFrame = new MainJFrame("Bomb It");
            map.setBounds(0, 0, 600, 600);
            map.setOpaque(false);
            map.setLayout(null);
            mainJFrame.add(map);
        });
        while (true) {
            sleep(10);
            if (mainJFrame != null) {
                map.repaint();
            }
        }
    }
}

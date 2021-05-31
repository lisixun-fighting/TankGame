package com.lsx.pojo;

import com.lsx.main.MyTankGame;

import java.awt.*;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Enemy extends Tank {

    public Enemy(int x, int y) {
        super(x, y);
    }

    public Enemy(int x, int y, int speed) {
        super(x, y);
        this.setSpeed(speed);
    }

    public Enemy(int x, int y, int speed, int direct) {
        super(x, y);
        this.setSpeed(speed);
        this.setDirect(direct);
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        if(getState() == 1)
            drawTank(g, this.getDirect());
        drawBullets(g);
    }

    public static void start(Vector<Tank> enemies) {
        MyTankGame.EXEC.execute(() -> {
            Random rand = new Random(47);
            while (!Thread.interrupted()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                    for (Tank enemy : enemies) {
                        if(enemy.getState() != 1) continue;
                        int choice = rand.nextInt(20);
                        if(choice < 4)
                            enemy.setDirect(choice);
                        else if(choice < 14)
                            enemy.fire(1000, 750, 7);
                        else
                            switch (enemy.getDirect()) {
                                case 0:
                                    if(enemy.getY() > 0)
                                        enemy.moveUp(enemies);
                                    break;
                                case 1:
                                    if(enemy.getX() < 920)
                                        enemy.moveRight(enemies);
                                    break;
                                case 2:
                                    if(enemy.getY() < 650)
                                        enemy.moveDown(enemies);
                                    break;
                                case 3:
                                    if(enemy.getX() > 0)
                                        enemy.moveLeft(enemies);
                                    break;
                                default:
                                    break;
                            }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

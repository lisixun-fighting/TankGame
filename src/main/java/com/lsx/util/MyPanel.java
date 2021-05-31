package com.lsx.util;

import com.lsx.factory.TankFactory;
import com.lsx.pojo.*;
import com.lsx.util.Recorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 坦克大战的绘图区域
 */
public class MyPanel extends JPanel implements Runnable {
    // 定义我的坦克
    Tank hero;
    Vector<Tank> enemies;
    Vector<Bang> bangs;
    int enemyTankNum = 5;
    Recorder recorder;
    TankFactory factory = new TankFactory();

    public MyPanel(Recorder recorder) {
        enemies = new Vector<>();
        bangs = new Vector<>();
        if(recorder == null)
            this.recorder = new Recorder("", enemyTankNum);
        else
            this.recorder = recorder;
        hero = factory.generate(TankFactory.Type.HERO, this.recorder.getHeroLocate()[0], this.recorder.getHeroLocate()[1], 15); // 初始化自己的坦克
        if(this.recorder.getHp() <= 0)
            hero.setState(2);
        enemyTankNum -= this.recorder.getEliminateTanks();
        for (int[] locates : this.recorder.getEnemyLocate())
            enemies.add(factory.generate(TankFactory.Type.ENEMY, locates[0], locates[1], 20));
        Enemy.start(enemies);
    }

    public void paint(Graphics g) {
        super.paint(g);
        // 绘制背景画面
        g.fillRect(0, 0, 1000, 750);
        for (Tank enemy : enemies) {
            // 敌人子弹是否击中英雄
            if (hero.getState() == 1 && hero.isHit(enemy.bullets)) {
                bangs.add(new Bang(hero.getX(), hero.getY()));
                recorder.hurt();
                if (recorder.getHp() <= 0)
                    hero.setState(2);
            }
            // 英雄子弹是否击中坦克
            if (enemy.getState() == 1 && enemy.isHit(hero.bullets)) {
                bangs.add(new Bang(enemy.getX(), enemy.getY()));
                enemy.setState(2);
                recorder.goal();
            }
            enemy.draw(g);
        }
        hero.draw(g);
        Iterator<Bang>  it2 = bangs.iterator();
        while (it2.hasNext()) {
            Bang bang = it2.next();
            if(bang.getLife() > 0)
                bang.draw(g);
            else
                it2.remove();
        }
        showInfo(g);
    }

    public KeyListener listener = new MyKeyListener();

    private class MyKeyListener implements KeyListener {
        public void keyTyped(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {
            if(hero.getState() == 2)
                return;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if(hero.getY() > 0) {
                        hero.moveUp(enemies);
                        hero.setDirect(0);
                    }
                    break;
                case KeyEvent.VK_D:
                    if(hero.getX() < 920) {
                        hero.moveRight(enemies);
                        hero.setDirect(1);
                    }
                    break;
                case KeyEvent.VK_S:
                    if(hero.getY() < 650) {
                        hero.moveDown(enemies);
                        hero.setDirect(2);
                    }
                    break;
                case KeyEvent.VK_A:
                    if(hero.getX() > 0) {
                        hero.moveLeft(enemies);
                        hero.setDirect(3);
                    }
                    break;
                case KeyEvent.VK_J:
                    hero.fire(1000, 750, 7);
                    break;
                default:
                    System.out.println("不支持按键");
            }
        }
        public void keyReleased(KeyEvent e) {}
    }

    public KeyListener getListener() {
        return listener;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
                repaint();
                store();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void store() {
        recorder.setLocations(enemies, hero);
    }

    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累计击毁敌方坦克", 1020, 60);
        g.drawString(recorder.getEliminateTanks()+" 辆",1100,130);
        g.drawString("您当前生命值", 1020, 240);
        g.drawString(recorder.getHp() + " 命", 1100, 310);

        new Hero(1020, 270).draw(g);
        new Enemy(1020,90).draw(g);


    }

    public void close() {
        recorder.write();
        System.out.println("保存成功");
    }
}

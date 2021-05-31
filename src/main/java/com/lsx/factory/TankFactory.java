package com.lsx.factory;

import com.lsx.pojo.Enemy;
import com.lsx.pojo.Hero;
import com.lsx.pojo.Tank;

public class TankFactory {
    public enum Type {
        HERO,
        ENEMY
    }
    public Tank generate(Type type, int x, int y, int speed) {
        switch (type) {
            case HERO:
                return new Hero(x, y, speed);
            case ENEMY:
                return new Enemy(x, y, speed);
            default:
                throw new RuntimeException();
        }
    }
}

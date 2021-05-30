package com.lsx.pojo;

import java.awt.*;

public class Bang {

    static Image image1 = Toolkit.getDefaultToolkit().
            getImage(Bang.class.getResource("/bomb.gif"));
    static Image image2 = Toolkit.getDefaultToolkit().
            getImage(Bang.class.getResource("/bomb.gif"));
    static Image image3 = Toolkit.getDefaultToolkit().
            getImage(Bang.class.getResource("/bomb.gif"));

    private int x, y;
    private int life = 60;

    public Bang(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getLife() {
        return life;
    }

    public void draw(Graphics g) {
        if(life-- > 40)
            g.drawImage(image1, x, y, null);
        else if(life > 20)
            g.drawImage(image2, x+10, y+10, null);
        else
            g.drawImage(image3, x+20, y+20, null);
    }
}

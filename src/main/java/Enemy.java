import java.awt.*;

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

    @Override
    protected void draw(Graphics g) {
        g.setColor(Color.CYAN);
        if(getState() == 1)
            drawTank(g, this.getDirect());
        drawBullets(g);
    }
}

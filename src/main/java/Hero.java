import java.awt.*;

public class Hero extends Tank {

    public Hero(int x, int y) {
        super(x, y);
    }

    public Hero(int x, int y, int speed) {
        super(x, y);
        this.setSpeed(speed);
    }

    @Override
    protected void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        if(getState() == 1)
            drawTank(g, this.getDirect());
        drawBullets(g);
    }

}

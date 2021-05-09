public class Bullet{
    private int x = -10;
    private int y = -10;
    private int state; // state描述子弹状态，0代表就绪，1代表有效，2代表清出
    private int speed = 6;

    static Bullet next(int speed) {
        return new Bullet(1, speed);
    }

    public Bullet(int state) {
        this.state = state;
    }

    public Bullet(int state, int speed) {
        this.state = state;
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

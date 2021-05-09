import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

public abstract class Tank {
    private int x;
    private int y;
    private static int count = 0;
    private int direct; // 坦克方向
    private int speed;
    public Vector<Bullet> bullets;
    private int state = 1;

    private final int id;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        direct = 0;
        id = count++;
        bullets = new Vector<>();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getId() {
        return id;
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

    protected abstract void draw(Graphics g);

    /**
     * 利用设定颜色的画笔和方向画出坦克
     * @param g 画笔
     * @param direct 坦克方向
     */
    protected void drawTank(Graphics g, int direct) {
        // direct 表示方向
        // 0: 向上  1: 向右  2: 向下  3: 向左
        switch (direct) {
            case 0:
                g.fill3DRect(getX(), getY(),10,60, false);
                g.fill3DRect(getX()+40, getY(),10,60, false);
                g.fill3DRect(getX()+10,getY()+10,30,40,false);
                g.fillOval(getX()+15,getY()+20,20,20);
                g.fillRect(getX()+24,getY()-5,3,35);
                break;
            case 1:
                g.fill3DRect(getX(), getY(),60,10, false);
                g.fill3DRect(getX(), getY()+40,60,10, false);
                g.fill3DRect(getX()+10,getY()+10,40,30,false);
                g.fillOval(getX()+20,getY()+15,20,20);
                g.fillRect(getX()+30,getY()+24,35,3);
                break;
            case 2:
                g.fill3DRect(getX(), getY(),10,60, false);
                g.fill3DRect(getX()+40, getY(),10,60, false);
                g.fill3DRect(getX()+10,getY()+10,30,40,false);
                g.fillOval(getX()+15,getY()+20,20,20);
                g.fillRect(getX()+24,getY()+30,3,35);
                break;
            case 3:
                g.fill3DRect(getX(), getY(),60,10, false);
                g.fill3DRect(getX(), getY()+40,60,10, false);
                g.fill3DRect(getX()+10,getY()+10,40,30,false);
                g.fillOval(getX()+20,getY()+15,20,20);
                g.fillRect(getX()-5,getY()+24,35,3);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    protected void drawBullets(Graphics g) {
        Iterator<Bullet> it = bullets.iterator();
        while(it.hasNext()) {
            Bullet bullet = it.next();
            // 子弹状态1绘制
            if(bullet.getState() == 1)
                g.fillOval(bullet.getX(), bullet.getY(), 10, 10);
            // 子弹状态2将其移除出容器
            if(bullet.getState() == 2) it.remove();
        }
    }

    /**
     * 发射子弹
     * @param MAX_X 边框界限
     * @param MAX_Y 边框界限
     */
    public void fire(int MAX_X, int MAX_Y, int bulletSpeed) {
        if(bullets.size() > 20) return;
        new Thread(() -> {
            int direct = getDirect();
            int x = getX();
            int y = getY();
            switch (direct) {
                case 0:
                    x += 20;
                    y += 10;
                    break;
                case 1:
                    x += 40;
                    y += 20;
                    break;
                case 2:
                    x += 20;
                    y += 50;
                    break;
                case 3:
                    y += 20;
                    x += 10;
                    break;
                default:
                    break;
            }
            Bullet bullet = Bullet.next(bulletSpeed);
            bullets.add(bullet);
            while (x < MAX_X && x > 0 && y < MAX_Y && y > 0) {
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch (direct) {
                    case 0:
                        y -= bullet.getSpeed();
                        break;
                    case 1:
                        x += bullet.getSpeed();
                        break;
                    case 2:
                        y += bullet.getSpeed();
                        break;
                    case 3:
                        x -= bullet.getSpeed();
                        break;
                    default:
                        throw new UnsupportedOperationException("无该方向");
                }
                bullet.setX(x);
                bullet.setY(y);
            }
            bullet.setState(2);
        }).start();
    }

    // 上右下左移动方法
    public void moveUp(Vector<Tank> tanks, Tank hero) {
        boolean flag = true;
        for (Tank tank : tanks) {
            if(tank == this || tank.state != 1) continue;
            if (tank.getY() + 65 > this.getY() - speed &&
                    tank.getY() + 55 < this.getY() &&
                    tank.getX() + 65 > this.getX() &&
                    tank.getX() - 65 < this.getX()) {
                flag = false;
                break;
            }
        }
        if(flag)
            y -= speed;
    }

    public void moveRight(Vector<Tank> tanks, Tank hero) {
        boolean flag = true;
        for (Tank tank : tanks) {
            if(tank == this || tank.state != 1) continue;
            if (tank.getY() + 65 > this.getY() &&
                    tank.getY() - 65 < this.getY() &&
                    tank.getX() > this.getX() + 55 &&
                    tank.getX() - speed < this.getX() + 65) {
                flag = false;
                break;
            }
        }
        if(flag)
            x += speed;
    }

    public void moveDown(Vector<Tank> tanks, Tank hero) {
        boolean flag = true;
        for (Tank tank : tanks) {
            if(tank == this || tank.state != 1) continue;
            if (tank.getY() - 65 < this.getY() + speed &&
                    tank.getY() - 55 > this.getY() &&
                    tank.getX() + 65 > this.getX() &&
                    tank.getX() - 65 < this.getX()) {
                flag = false;
                break;
            }
        }
        if(flag)
            y += speed;
    }

    public void moveLeft(Vector<Tank> tanks, Tank hero) {
        boolean flag = true;
        for (Tank tank : tanks) {
            if(tank == this || tank.state != 1) continue;
            if (tank.getY() + 65 > this.getY() &&
                    tank.getY() - 65 < this.getY() &&
                    tank.getX() + 55 < this.getX() &&
                    tank.getX() + 65 > this.getX() - speed) {
                flag = false;
                break;
            }
        }
        if(flag)
            x -= speed;
    }


    public boolean isHit(Vector<Bullet> bullets) {
        for (Bullet b : bullets) {
            if(b.getState() == 1)
                switch (direct) {
                    case 0:
                    case 2:
                        if(b.getX() >= x-5 && b.getX() <= x + 45
                                && b.getY() >= y-5 && b.getY() <= y + 55) {
                            b.setState(2);
                            return true;
                        }
                        break;
                    case 1:
                    case 3:
                        if(b.getX() >= x-5 && b.getX() <= x + 55
                                && b.getY() >= y-5 && b.getY() <= y + 45) {
                            b.setState(2);
                            return true;
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException();
                }
        }
        return false;
    }
}

package Client.TankAZ;

import java.awt.*;

public class MainObject {
    private int x, y;
    public static int speed = 4;
    int direction;
     boolean fire;
    private int stack;
     long current;
    private long delay;
     boolean isMove = false;

     public MainObject() {};

    public MainObject(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        current = System.nanoTime();
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

    public void tick() {
        if (isMove)
            switch (direction) {
                case GameAZScreen.TOP:
                    y-=speed;
                    break;
                case GameAZScreen.RIGHT:
                    x+=speed;
                    break;
                case GameAZScreen.BOTTOM:
                    y+=speed;
                    break;
                case GameAZScreen.LEFT:
                    x-=speed;
                    break;
            }
    }

    public void render(Graphics graphics) {

    }
}

package Client.TankAZ;

import java.awt.*;

public class Bullet {

    boolean isPlayerFire;
    private int x, y;
    int direction;
    public static int speed;
    public Bullet(int x, int y, int direction, boolean flag) {
        this.x = x;
        this.y = y;
        speed = 7;
        this.direction = direction;
        isPlayerFire = flag;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void tick() {
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
        switch (direction) {
            case GameAZScreen.TOP:
                graphics.drawImage(loadImage.bullet[GameAZScreen.TOP], x, y, 40, 40, null);
                break;
            case GameAZScreen.RIGHT:
                graphics.drawImage(loadImage.bullet[GameAZScreen.RIGHT], x, y, 40, 40, null);
                break;
            case GameAZScreen.BOTTOM:
                graphics.drawImage(loadImage.bullet[GameAZScreen.BOTTOM], x, y, 40, 40, null);
                break;
            case GameAZScreen.LEFT:
                graphics.drawImage(loadImage.bullet[GameAZScreen.LEFT], x, y, 40, 40, null);
                break;
        }
    }

}

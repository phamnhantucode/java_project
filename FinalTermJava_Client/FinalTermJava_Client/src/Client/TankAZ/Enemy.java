package Client.TankAZ;

import java.awt.*;

public class Enemy extends MainObject{
    public Enemy(int x, int y, int direction) {
        super(x, y, direction);
    }

    public Enemy() {

    }

    public void render(Graphics graphics) {
//        super.render(graphics);
        switch (direction) {
            case GameAZScreen.TOP:
                graphics.drawImage(loadImage.enemy[GameAZScreen.TOP], this.getX(), this.getY(), 40, 40, null);
                break;
            case GameAZScreen.RIGHT:
                graphics.drawImage(loadImage.enemy[GameAZScreen.RIGHT], this.getX(), this.getY(), 40, 40, null);
                break;
            case GameAZScreen.BOTTOM:
                graphics.drawImage(loadImage.enemy[GameAZScreen.BOTTOM],this.getX(), this.getY(), 40, 40, null);
                break;
            case GameAZScreen.LEFT:
                graphics.drawImage(loadImage.enemy[GameAZScreen.LEFT], this.getX(), this.getY(), 40, 40, null);
                break;
            default:
        }
    }
}

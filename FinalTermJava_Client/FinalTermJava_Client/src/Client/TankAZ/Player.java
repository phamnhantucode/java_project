package Client.TankAZ;

import Client.ClientView;
//import Server.ClientCon;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends MainObject implements KeyListener {
    boolean isTopPressing;
    boolean isRightPressing;
    boolean isBottomPressing;
    boolean isLeftPressing;
    int stack = 3;
    long delay = 10000;
    public Player() {

        GameAZScreen.frame.addKeyListener(this);
    }
    public Player(int x, int y, int direction) {
        super(x, y, direction);
        GameAZScreen.frame.addKeyListener(this);
    }

    @Override
    public void render(Graphics graphics) {
//        super.render(graphics);
        switch (direction) {
            case GameAZScreen.TOP:
                graphics.drawImage(loadImage.player[GameAZScreen.TOP], this.getX(), this.getY(), 40, 40, null);
                break;
            case GameAZScreen.RIGHT:
                graphics.drawImage(loadImage.player[GameAZScreen.RIGHT], this.getX(), this.getY(), 40, 40, null);
                break;
            case GameAZScreen.BOTTOM:
                graphics.drawImage(loadImage.player[GameAZScreen.BOTTOM],this.getX(), this.getY(), 40, 40, null);
                break;
            case GameAZScreen.LEFT:
                graphics.drawImage(loadImage.player[GameAZScreen.LEFT], this.getX(), this.getY(), 40, 40, null);
                break;
            default:
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            direction = 1;
            isTopPressing = true;
            isMove = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direction = 2;
            isRightPressing = true;
            isMove = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            direction = 3;
            isBottomPressing = true;
            isMove = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direction = 4;
            isLeftPressing = true;
            isMove = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            long breaks = (System.nanoTime() - current)/1000000;
            if (breaks > delay && stack > 0 && RoomAZ.azSetUp.manager.notifi.equals("")) {
                AZManager.bullets.add(new Bullet(getX(), getY(), direction, true));
                ClientView.getInstance().serverCon.service.addBullet(getX(), getY(), direction);
                stack--;
                RoomAZ.azSetUp.gameAZScreen.setStackBullet(String.valueOf(stack));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_8) {
            isTopPressing = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_6) {
            isRightPressing = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            isBottomPressing = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_4) {
            isLeftPressing = false;
        }
        if (!isLeftPressing && !isBottomPressing && !isRightPressing && !isTopPressing)
            isMove = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        }
    }
}

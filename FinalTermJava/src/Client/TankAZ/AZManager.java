package Client.TankAZ;

import Client.ClientView;
import Network.Message;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AZManager implements KeyListener {
    String notifi;
    public Player player = new Player();
    private Enemy enemy = new Enemy();
    public static ArrayList<Bullet> bullets;
    boolean drawString = false;
    private long current, delay;
    private boolean start;
    public AZManager() {

    }
    public void init() {
        GameAZScreen.frame.addKeyListener(this);
        bullets = new ArrayList<>() {
            @Override
            public Bullet remove(int index) {
                Bullet bullet = super.remove(index);
                if (bullet.isPlayerFire) {
                    player.stack++;
                    RoomAZ.azSetUp.gameAZScreen.setStackBullet(String.valueOf(player.stack));
                }
                return bullet;
            }
        };
        current = System.nanoTime();
        delay = 2000;

    }

    public void tick() {
        if (true) {
            player.tick();

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).tick();
            }

        }
    }

    public void render(Graphics g) {
        player.render(g);
        enemy.render(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            int x = bullets.get(i).getX();
            int y = bullets.get(i).getY();
            int d = bullets.get(i).direction;
            if (d == 1 && y < 0) bullets.remove(i);
            if (d == 2 && x > 460) bullets.remove(i);
            if (d == 3 && y > 460) bullets.remove(i);
            if (d == 4 && x < 0) bullets.remove(i);
        }
        for (int i = 0; i < bullets.size(); i++) {
            int x = bullets.get(i).getX();
            int y = bullets.get(i).getY();
            int d = bullets.get(i).direction;
            if (bullets.get(i).isPlayerFire)
                switch (d) {
                    case 1:
                        switch (enemy.direction) {
                            case 1:
                                x += 20;
                                if (x >= enemy.getX() + 5 && x <= enemy.getX() + 35 && y <= enemy.getY() + 30 && y >= enemy.getY()) {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }

                                break;
                            case 2:
                                x += 20;
                                if (x >= enemy.getX()&& x <= enemy.getX() + 35 && y <= enemy.getY() + 25 && y >= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 3:
                                x += 20;
                                if (x >= enemy.getX() + 5 && x <= enemy.getX() + 35 && y <= enemy.getY() + 25 && y >= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 4:
                                x += 20;
                                if (x >= enemy.getX() + 10 && x <= enemy.getX() + 41 && y <= enemy.getY() + 25 && y >= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                        }
                        switch (enemy.direction) {
                            case 1:
                                x += 20;
                                if (x >= enemy.getX() + 5 && x <= enemy.getX() + 35 && y <= enemy.getY() + 30 && y >= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 2:
                                x += 20;
                                if (x >= enemy.getX()&& x <= enemy.getX() + 35 && y <= enemy.getY() + 25 && y >= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 3:
                                x += 20;
                                if (x >= enemy.getX() + 5 && x <= enemy.getX() + 35 && y <= enemy.getY() + 25 && y >= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 4:
                                x += 20;
                                if (x >= enemy.getX() + 10 && x <= enemy.getX() + 41 && y <= enemy.getY() + 25 && y >= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                        }
                        break;
                    case 2:
                        switch (enemy.direction) {
                            case 1:
                                y += 20;
                                if (y >= enemy.getY() + 10 && y <= enemy.getY() + 38 && x >= enemy.getX() - 20 && x <= enemy.getX() + 30)
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 2:
                                y += 20;
                                if (y >= enemy.getY() + 10 && y <= enemy.getY() + 35 && x >= enemy.getX()- 30 && x <= enemy.getX())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 3:
                                y += 20;
                                if (y >= enemy.getY() && y <= enemy.getY() + 30 && x >= enemy.getX() - 20 && x <= enemy.getX() + 30)
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 4:
                                y += 20;
                                if (y >= enemy.getY() + 10 && y <= enemy.getY() + 35 && x >= enemy.getX()- 25 && x <= enemy.getX())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                        }
                        break;
                    case 3:
                        switch (enemy.direction) {
                            case 1:
                                x += 20;
                                if (x >= enemy.getX() + 5 && x <= enemy.getX() + 35 && y >= enemy.getY() - 20 && y <= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 2:
                                x += 20;
                                if (x >= enemy.getX()&& x <= enemy.getX() + 35 && y >= enemy.getY() - 20  && y <= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 3:
                                x += 20;
                                if (x >= enemy.getX() + 5 && x <= enemy.getX() + 35 && y >= enemy.getY() -35 && y <= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 4:
                                x += 20;
                                if (x >= enemy.getX() + 10 && x <= enemy.getX() + 41 && y >= enemy.getY() - 20 && y <= enemy.getY())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                        }
                        break;
                    case 4:
                        switch (enemy.direction) {
                            case 1:
                                y += 20;
                                if (y >= enemy.getY() + 10 && y <= enemy.getY() + 38 && x <= enemy.getX()  + 23 && x >= enemy.getX())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 2:
                                y += 20;
                                if (y >= enemy.getY() + 10 && y <= enemy.getY() + 35 && x <= enemy.getX() + 25 && x >= enemy.getX())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 3:
                                y += 20;
                                if (y >= enemy.getY() && y <= enemy.getY() + 30 && x <= enemy.getX() + 23 && x >= enemy.getX())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                            case 4:
                                y += 20;
                                if (y >= enemy.getY() + 10 && y <= enemy.getY() + 35 && x <= enemy.getX() + 30 && x >= enemy.getX())
                                {
                                    bullets.remove(i);
                                    ClientView.getInstance().serverCon.service.defeatEnemyAZ();
                                    gameEnd(enemy);
                                }
                                break;
                        }
                        break;
                }
            else {
                switch (d) {
                    case 1:
                        switch (player.direction) {
                            case 1:
                                x += 20;
                                if (x >= player.getX() + 5 && x <= player.getX() + 35 && y <= player.getY() + 30 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 2:
                                x += 20;
                                if (x >= player.getX()&& x <= player.getX() + 35 && y <= player.getY() + 25 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 3:
                                x += 20;
                                if (x >= player.getX() + 5 && x <= player.getX() + 35 && y <= player.getY() + 25 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 4:
                                x += 20;
                                if (x >= player.getX() + 10 && x <= player.getX() + 41 && y <= player.getY() + 25 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                        }
                        switch (player.direction) {
                            case 1:
                                x += 20;
                                if (x >= player.getX() + 5 && x <= player.getX() + 35 && y <= player.getY() + 30 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 2:
                                x += 20;
                                if (x >= player.getX()&& x <= player.getX() + 35 && y <= player.getY() + 25 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 3:
                                x += 20;
                                if (x >= player.getX() + 5 && x <= player.getX() + 35 && y <= player.getY() + 25 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 4:
                                x += 20;
                                if (x >= player.getX() + 10 && x <= player.getX() + 41 && y <= player.getY() + 25 && y >= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                        }
                        break;
                    case 2:
                        switch (player.direction) {
                            case 1:
                                y += 20;
                                if (y >= player.getY() + 10 && y <= player.getY() + 38 && x >= player.getX() - 20 && x <= player.getX() + 30)
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 2:
                                y += 20;
                                if (y >= player.getY() + 10 && y <= player.getY() + 35 && x >= player.getX()- 30 && x <= player.getX())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 3:
                                y += 20;
                                if (y >= player.getY() && y <= player.getY() + 30 && x >= player.getX() - 20 && x <= player.getX() + 30)
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 4:
                                y += 20;
                                if (y >= player.getY() + 10 && y <= player.getY() + 35 && x >= player.getX()- 25 && x <= player.getX())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                        }
                        break;
                    case 3:
                        switch (player.direction) {
                            case 1:
                                x += 20;
                                if (x >= player.getX() + 5 && x <= player.getX() + 35 && y >= player.getY() - 20 && y <= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 2:
                                x += 20;
                                if (x >= player.getX()&& x <= player.getX() + 35 && y >= player.getY() - 20  && y <= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 3:
                                x += 20;
                                if (x >= player.getX() + 5 && x <= player.getX() + 35 && y >= player.getY() -35 && y <= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 4:
                                x += 20;
                                if (x >= player.getX() + 10 && x <= player.getX() + 41 && y >= player.getY() - 20 && y <= player.getY())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                        }
                        break;
                    case 4:
                        switch (player.direction) {
                            case 1:
                                y += 20;
                                if (y >= player.getY() + 10 && y <= player.getY() + 38 && x <= player.getX()  + 23 && x >= player.getX())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 2:
                                y += 20;
                                if (y >= player.getY() + 10 && y <= player.getY() + 35 && x <= player.getX() + 25 && x >= player.getX())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 3:
                                y += 20;
                                if (y >= player.getY() && y <= player.getY() + 30 && x <= player.getX() + 23 && x >= player.getX())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                            case 4:
                                y += 20;
                                if (y >= player.getY() + 10 && y <= player.getY() + 35 && x <= player.getX() + 30 && x >= player.getX())
                                {
                                    bullets.remove(i);
                                    gameEnd(player);
                                }
                                break;
                        }
                        break;
                }
            }
        }


        if (drawString) {
            g.setColor(Color.black);
            g.setFont(new Font("Aria", Font.PLAIN, 24));
            g.drawString(notifi, 100, 300);
            g.drawString("X:" + player.getX() + " Y:" + player.getY(), 100, 100);
        }
    }

    private void gameEnd(MainObject losser) {
        bullets.removeAll(bullets);
        player.isMove = false;
        enemy.isMove = false;
        if (losser instanceof Player) {
            loadImage.player[player.direction] = loadImage.imageLoader("/player" + player.direction + "-toang.png");
        }
        if (losser instanceof Enemy) {
            loadImage.enemy[enemy.direction] = loadImage.imageLoader("/enemy" + enemy.direction + "-toang.png");
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setPosEnemy(Message m) {
        try {

            enemy.setX(m.reader().readInt());
            enemy.setY(m.reader().readInt());
            enemy.direction = m.reader().readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosPlayer(Message m) {
        try {
            player.setX(m.reader().readInt());
            player.setY(m.reader().readInt());
            player.direction = m.reader().readInt();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawString(String readUTF) {
        if (readUTF.equals("Ready to start new game")) {
        	loadImage.reset();
            RoomAZ.azSetUp.gameAZScreen.setEnableBtnReady();
        }
        notifi = readUTF;
        drawString = true;
    }
}

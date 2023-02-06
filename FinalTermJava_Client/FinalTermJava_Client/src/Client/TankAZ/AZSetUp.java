package Client.TankAZ;

import Client.ClientView;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class AZSetUp implements Runnable{
    String name;
    String pass;
    int bet;

    private int width, height;
    private Thread thread;
    private boolean running;
     GameAZScreen gameAZScreen;
    private BufferStrategy bufferStrategy;
    public AZManager manager = new AZManager();
    private Graphics g;

    public AZSetUp(int width, int height, String name, String pass, int bet) {
        this.width = width;
        this.height = height;
        this.name = name;
        this.pass = pass;
        this.bet = bet;
    }

    public void init() {
        gameAZScreen = new GameAZScreen(width, height, this);
        loadImage.init();
        manager.init();
    }

    public synchronized void start() {
        if (running)  {
            return;
        }
        running = true;
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
    public synchronized  void stop(){
        if (!(running))
            return;
        running = false;
        try {
        	
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void tick() {
        manager.tick();
    }

    public void render() {
        bufferStrategy = gameAZScreen.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            gameAZScreen.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bufferStrategy.getDrawGraphics();
        g.clearRect(50, 50, 500, 500);
        g.drawImage(loadImage.image, 0, 0, 500, 500, null);
        manager.render(g);

        bufferStrategy.show();
        g.dispose();

    }


    @Override
    public void run() {
        init();
        int fps = 50;
        double timePerTick = 1000000000/fps;
        double delta = 0;
        long current = System.nanoTime();
        int count = 0;
        while (running) {
            delta = delta + (System.nanoTime() - current) / timePerTick;
            current = System.nanoTime();
            if (delta >=1) {
                count ++;
                if (count == 3) {
                    ClientView.getInstance().serverCon.service.sendPosAZ(manager.player.getX(), manager.player.getY(), manager.player.direction);
                    count = 0;
                }
                tick();
                render();
                delta--;
            }

        }
    }

}

package Client.TankAZ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class loadImage {
    public static BufferedImage image,entities,iteam,health;
    public static BufferedImage[] bullet = new BufferedImage[5], player = new BufferedImage[5], enemy = new BufferedImage[5];
    public static void init(){
    	reset();
//        bullet[1] = imageLoader("/TankAZ/bullet1.png");
//        bullet[2] = imageLoader("/TankAZ/bullet2.png");
//        bullet[3] = imageLoader("/TankAZ/bullet3.png");
//        bullet[4] = imageLoader("/TankAZ/bullet4.png");
//        player[1] = imageLoader("/TankAZ/player1.png");
//        player[2] = imageLoader("/TankAZ/player2.png");
//        player[3] = imageLoader("/TankAZ/player3.png");
//        player[4] = imageLoader("/TankAZ/player4.png");
//        entities = imageLoader("/player.png");
//        iteam = imageLoader("/x3.png");
//        health = imageLoader("/heart.png");
    }
    public static BufferedImage imageLoader(String path){
        try{
            return ImageIO.read(loadImage.class.getResource(path));
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    
    public static void reset() {
    	image = imageLoader("/map-fight.png");
        for (int i = 1; i <= 4 ; i++) {
            bullet[i] = imageLoader("/bullet" + i + ".png");
            player[i] = imageLoader("/player" + i + ".png");
            enemy[i] = imageLoader("/enemy" + i + ".png");
        }
    }
}

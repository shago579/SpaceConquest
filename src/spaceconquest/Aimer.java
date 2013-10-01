package spaceconquest;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Aimer {
    int height;
    int width;
    int  x;
    int  y;
    int speed = 10;
    private URL url2; 
    
    public BufferedImage bffimg;
    
    
    public Aimer(int x, int y,int player){
        this.x = x;
        this.y = y;
        String url = "../img/Aim"+player+".png";
        try {
            url2 = this.getClass().getResource(url);
            bffimg = ImageIO.read(url2);
        } catch (IOException ex) {
            Logger.getLogger(Aimer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void moveRight(){
        this.x += this.speed;
        if(this.x > 570) this.x = 570;
    }
    public void moveLeft(){
        this.x -= this.speed;
        if(this.x < 0) this.x = 0;
    }
    public void moveUp(){
        
    }
    public void moveDown(){
        
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public void drawAimer(){
        
    }
    
    public void shoot(){
        
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}

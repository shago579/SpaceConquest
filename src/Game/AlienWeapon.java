/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Uriel
 */
public class AlienWeapon implements Runnable{
    private int x;
    public BufferedImage img;
    public BufferedImage img_copy;
    public BufferedImage img_buffered;
    private String url = "Player2Arm_test.png";
    private int y;
    private int width = 10;
    private int height = 7;
    public double angle;
    private Alien sp;
    boolean is_alive = true;
    public AlienWeapon(int x, int y, Alien a) throws IOException {
        this.sp = a;
        this.img = new BufferedImage(570, 380, BufferedImage.TRANSLUCENT);
        img_buffered = ImageIO.read(new File(this.url));
        img.setData(img_buffered.getRaster());
        this.y = y;
        this.x = x;
        
    }
    @Override
    public void run() {
        while(is_alive){
            try{
                Thread.sleep(1000 / 60);             
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }
    public int get_x() {
        return x;
    }

    public int get_y() {
        return y;
    }
    public int get_width() {
        return width;
    }

    public int get_height() {
        return height;
    }
    public Image get_img(){
        return img;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void update_angle(double width, double height ) throws IOException{
        
        this.angle = Math.toDegrees(Math.atan(height/width));
        AffineTransform trans = new AffineTransform();
        trans.rotate( Math.toRadians(angle),this.get_width()+120,this.get_height()+100);
        
        img_copy = new BufferedImage(570, 380, BufferedImage.TRANSLUCENT);
        Graphics g = img_copy.createGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img_buffered, trans,null);
        
        img.setData(img_copy.getRaster());
    }
}

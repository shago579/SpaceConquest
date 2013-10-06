/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Uriel
 */
public class Weapon {
    /*
     * Defines the necessary variables for the Weapon to work
     */
    private int x;
    public BufferedImage img;
    public BufferedImage img_copy;
    public BufferedImage img_buffered;
    private String url = "Player1Arm.png";
    private int y;
    private int width = 10;
    private int height = 7;
    public double angle;
    public int y_limit;
    public Character character;
    boolean is_alive = true;
    public Weapon(int x, int y, String url, Character ch) throws IOException {
        //Creates a new weapon.
        this.url = url;
        this.character = ch;
        this.img = new BufferedImage(570, 380, BufferedImage.TRANSLUCENT);
        img_buffered = ImageIO.read(new File(this.url));
        this.y = y;
        this.y_limit = y;
        this.x = x;
        
    }

    public int get_x() {
        //Returns the x value of the weapon
        return x;
    }

    public int get_y() {
        //Returns the y value of the weapon
        return y;
    }
    public int get_width() {
        //Returns the width value of the weapon
        return width;
    }

    public int get_height() {
        //Returns the height value of the weapon
        return height;
    }
    public Image get_img(){
        //Returns the image of the weapon
        return img;
    }
    public void setX(int x) {
        //Changes the x value of the weapon
        this.x = x;
    }

    public void setY(int y) {
        //Returns the y value of the weapon
        this.y = y;
    }
    public void update_angle(double width, double height ) throws IOException{
        //Turns the weapon depending of the position of the control.
        this.angle = Math.toDegrees(Math.atan(height/width));
        AffineTransform trans = new AffineTransform();
        if(this.character.getClass().getName().equals("Game.Alien")){
            trans.rotate( Math.toRadians(angle),this.get_width()+100,this.get_height()+100);
        }
        else{
            trans.rotate( Math.toRadians(angle),this.get_width(),this.get_height()+100);
        }
        
        img_copy = new BufferedImage(570, 380, BufferedImage.TRANSLUCENT);
        Graphics g = img_copy.createGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img_buffered, trans,null);
        
        img.setData(img_copy.getRaster());
    }
}

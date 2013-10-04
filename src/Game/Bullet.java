/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Uriel
 */
public class Bullet implements Collisionable,Runnable{
    private int x;
    public Image img;
    private String url = "img/nota.png";
    private int limite = 0;
    private int y;
    private int width = 10;
    private int height = 20;
    private int x_speed;
    private int y_speed;
    private Spaceman sp;
    boolean is_alive = true;
    private int gravity = 0;
    public Weapon weapon;
    public Bullet(int x, int y, int y_speed, int x_speed, Weapon w) {
        this.weapon = w;
        this.img = new ImageIcon(getClass().getClassLoader().getResource(this.url)).getImage();
        this.y = y;
        this.x = x;
        this.x_speed = x_speed;
        
        this.y_speed = -y_speed;
        
    }
    @Override
    public void run() {
        while(is_alive){
            
            try{
                Thread.sleep(1000 / 60);
                this.y_speed -=  this.gravity;
                this.y-= this.y_speed;
                this.x += x_speed;
                if(this.x > 2000 || this.y > 1000) this.is_alive = false;
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
    public void hit(){
        //Sounds, animations etc.
        this.is_alive = false;
        this.weapon.character.removeBullet(this);
        
        
    }
}

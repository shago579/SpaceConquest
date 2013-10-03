/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;

/**
 *
 * @author Uriel
 */
public class Spaceman implements Collisionable{
    public static int height = 20;
    public static int width = 15;
    private int  x;
    private int  y;
    private int speed = 10;
    private int misiles = 5;
    public Weapon weapon;
    public Image img = null;

    public Spaceman(int x, int y,int limite) throws IOException{
        this.x = x;
        this.y = y;
        this.img = new ImageIcon(getClass().getClassLoader().getResource("img/Player1Body.png")).getImage();
        weapon = new Weapon(this.get_x() + 7,this.get_y() - 60,this);
    }
    
    public void move_right(){
        this.x += this.speed;
        if(this.x > 570) this.x = 570;
    }
    public void move_left(){
        this.x -= this.speed;
        if(this.x < 0) this.x = 0;
    }
    
    public void shoot(){
        
    }
    public void shoot_missile(){
        
    }

    @Override
    public int get_x() {
        return this.x;
    }

    @Override
    public int get_y() {
        return this.y;
    }

    @Override
    public int get_width() {
        return this.width;
    }

    @Override
    public int get_height() {
        return this.height;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Uriel
 */
public class Spaceman implements Collisionable{
    public static int height = 90;
    public static int width = 55;
    private int  x;
    private int  y;
    public int limite_x,limite_y;
    private int speed = 10;
    private int misiles = 5;
    public Weapon weapon;
    public Image img = null;
    public ArrayList<Bullet> bullets;
    
    public Spaceman(int x, int y,int limite_x,int limite_y) throws IOException{
        this.x = x;
        this.y = y;
        this.img = new ImageIcon(getClass().getClassLoader().getResource("img/Player1Body.png")).getImage();
        weapon = new Weapon(this.get_x() + 7,this.get_y() - 60,this);
        bullets = new ArrayList<Bullet>();
    }
    
    public void move_right(){
        this.x += this.speed;
        this.weapon.setX(this.weapon.get_x()+this.speed);
    }
    public void move_left(){
        this.x -= this.speed;
        if(this.x < 0){
            this.x = 0;
        } 
        else{
            this.weapon.setX(this.weapon.get_x()-this.speed);
        }
    }
    
    public void shoot(int speed_y,int speed_x){
        Bullet b = new Bullet(this.get_x(),this.get_y(),speed_y,speed_x);
        Thread t = new Thread(b);
        this.bullets.add(b);
        t.start();
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

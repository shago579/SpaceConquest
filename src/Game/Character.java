/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Uriel
 */
public class Character implements Collisionable{
    protected int  x;
    protected int  y;
    public static int height = 90;
    public static int width = 55;
    public int limite_x,limite_y;
    protected int speed = 10;
    protected int misiles = 5;
    public Weapon weapon;
    protected int life = 100;
    protected Image img = null;
    protected ArrayList<Bullet> bullets;
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
    public int hurt(int hit, Bullet b){
        this.life -= hit;
        return this.life;
    }
    public void removeBullet(Bullet b){
        this.bullets.remove(b);
    }
    public void shoot(int speed_y,int speed_x){
        Bullet b = new Bullet(this.get_x(),this.get_y(),speed_y,speed_x,this.weapon);
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

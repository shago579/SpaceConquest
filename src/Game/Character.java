/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Uriel
 */
public class Character implements Collisionable,Runnable{
    protected int  x;
    protected int  y;
    public static int height = 90;
    public static int width = 55;
    public int limite_x,limite_y=0;
    protected int speed = 10;
    protected int misiles = 5;
    public Weapon weapon;
    public int gravity = 2;
    protected int life = 100;
    protected Image img = null;
    protected Image img_life = null;
    public int y_speed = 0;
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
    public Image get_image_life(){
        if(this.life == 100)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life1.png")).getImage();
        if(this.life == 80)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life2.png")).getImage();
        if(this.life == 60)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life3.png")).getImage();
        if(this.life == 40)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life4.png")).getImage();
        if(this.life == 20)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life5.png")).getImage();
        if(this.life == 0)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life6.png")).getImage();
        return this.img_life;
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
    public void jump(){
        this.y_speed = 50;
    }
    @Override
    public int get_x() {
        return this.x;
    }

    @Override
    public int get_y() {
        return this.y;
    }
    
    public void set_x(int x) {
        this.x = x;
        weapon.setX(this.get_x() + 7);
    }

    
    public void set_y(int y) {
        this.y = y;
        weapon.setY(this.get_y() - 60);
    }
    @Override
    public int get_width() {
        return this.width;
    }

    @Override
    public int get_height() {
        return this.height;
    }

    @Override
    public void run() {
        while(life > 0){
            try{
                Thread.sleep(1000 / 60);
                
                this.y_speed -=  this.gravity;
                
                this.y-= this.y_speed;
                this.weapon.setY(this.weapon.get_y() - this.y_speed);
                if(this.y > this.limite_y){
                    this.y = this.limite_y;
                    this.weapon.setY(this.weapon.y_limit);
                    this.y_speed = 0;
                } 
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }
}

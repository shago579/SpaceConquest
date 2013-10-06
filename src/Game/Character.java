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
    /*
     * Defines the necessary variables for each CHaracter to work such as a Weapon, the limits, the gravity,
     * the image and so on.
     */
    protected int  x;
    protected int  y;
    public static int height = 90;
    public static int width = 55;
    public int limite_x,limite_y=0;
    public Weapon weapon;
    public int gravity = 2;
    protected int life = 100;
    protected Image img = null;
    private int speed = 15;
    protected Image img_life = null;
    public int y_speed = 0;
    protected ArrayList<Bullet> bullets;
    public void move_right(){
        //Moves the character to the right
        this.x += this.speed;
        this.weapon.setX(this.weapon.get_x()+this.speed);
    }
    public void move_left(){
        //Moves the character to the left
        this.x -= this.speed;
        if(this.x < 0){
            this.x = 0;
        } 
        else{
            this.weapon.setX(this.weapon.get_x()-this.speed);
        }
    }
    public Image get_image_life(){
        //Returns the image for the life depending of the current life of the character.

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
        //Changes the life of the character depending on the hit.
        this.life -= hit;
        return this.life;
    }
    public void removeBullet(Bullet b){
        //Destroys a bullet
        this.bullets.remove(b);
    }
    public void shoot(int speed_y,int speed_x){
        //Creates a new bullet
        
        Bullet b = new Bullet(this.get_x(),this.get_y(),speed_y,speed_x,this.weapon);
        Thread t = new Thread(b);
        this.bullets.add(b);
        t.start();
    }
    public void jump(){
        //Changes the y speed of the character in order to jump
        this.y_speed = 50;
    }
    @Override
    public int get_x() {
        //Returns the x value of the character
        return this.x;
    }

    @Override
    public int get_y() {
        //Returns the y value of the character
        return this.y;
    }
    
    public void set_x(int x) {
        //Sets the x value of the character
        //Also changes the position of the weapon
        this.x = x;
        weapon.setX(this.get_x() + 7);
    }

    
    public void set_y(int y) {
         //Sets the y value of the character
        //Also changes the position of the weapon
        this.y = y;
        weapon.setY(this.get_y() - 60);
    }
    @Override
    public int get_width() {
        //Returns the width value of the character
        return this.width;
    }

    @Override
    public int get_height() {
        //Returns the height value of the character
        return this.height;
    }

    @Override
    public void run() {
        //Animates the character when jumping
        while(life > 0){
            try{
                Thread.sleep(1000 / 60);
                this.y_speed -=  this.gravity;
                if(this.getClass().getName().equals("Game.Alien")){
                    this.weapon.setX(get_x() - 70);
                    this.weapon.setY(this.get_y() - 60);
                }
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

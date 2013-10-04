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
public class Alien extends Character{
    public static int height = 250;
    public static int width = 65;
    public Image winner_image;
    public Alien(int x, int y,int limite_x,int limite_y) throws IOException{
        /*
         * Creates a new Alien and adds his weapon.
         * Also initializes the image that appears when winning.
         */
        this.x = x;
        this.limite_y = y;
        this.y = y;
        this.img = new ImageIcon(getClass().getClassLoader().getResource("img/Player2Body.png")).getImage();
        this.winner_image = new ImageIcon(getClass().getClassLoader().getResource("img/player1win.png")).getImage();
        weapon = new Weapon(this.get_x() - 70,this.get_y() - 60,"Player2Arm_test.png",this);
        bullets = new ArrayList<Bullet>(); 
    
    } 
    public int get_width() {
        //Overrides the parent's method to change the width of the Alien
        return 65;
    }
   
    @Override
    public int get_height() {
        //Overrides the parent's method to change the height of the Alien
        return 100;
    }
    public Image get_image_life(){
        //Returns the image for the life depending of the current life of the character.
        //Overrides the parent method to change the image
        if(this.life == 100)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life7.png")).getImage();
        if(this.life == 80)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life8.png")).getImage();
        if(this.life == 60)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life9.png")).getImage();
        if(this.life == 40)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life10.png")).getImage();
        if(this.life == 20)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life11.png")).getImage();
        if(this.life == 0)
            this.img_life = new ImageIcon(getClass().getClassLoader().getResource("img/life12.png")).getImage();
        return this.img_life;
    }
}


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
   
    public Alien(int x, int y,int limite_x,int limite_y) throws IOException{
        this.x = x;
        this.y = y;
        this.img = new ImageIcon(getClass().getClassLoader().getResource("img/Player2Body.png")).getImage();
        weapon = new Weapon(this.get_x() - 70,this.get_y() - 60,"Player2Arm_test.png",this);
        bullets = new ArrayList<Bullet>(); 
    
    } 
    public int get_width() {
        return 65;
    }

    @Override
    public int get_height() {
        return 100;
    }
    
}


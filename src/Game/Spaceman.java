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
public class Spaceman extends Character{
    public static int height = 90;
    public static int width = 55;
    public Image winner_image;
    public Spaceman(int x, int y,int limite_x,int limite_y) throws IOException{
        this.winner_image = new ImageIcon(getClass().getClassLoader().getResource("img/player1win.png")).getImage();
        this.x = x;
        this.y = y;
        this.limite_y = y;
        this.img = new ImageIcon(getClass().getClassLoader().getResource("img/Player1Body.png")).getImage();
        weapon = new Weapon(this.get_x() + 7,this.get_y() - 60,"Player1Arm.png",this);
        bullets = new ArrayList<Bullet>();
    }
}

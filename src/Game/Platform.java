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
public class Platform implements Collisionable{
    private int x,y,width,height;
    private String url = "img/level.png";
    public Image img;
    public Platform(int x,int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = new ImageIcon(getClass().getClassLoader().getResource(this.url)).getImage();
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

package spaceconquest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

public class MenuButton {

    private int width = 275;
    private int height = 75;
    private URL url;
    
    private BufferedImage bffimg;
    
    private BufferedImage noSelected;
    private BufferedImage selected;
    private BufferedImage nothing;
    
    private int x;
    private int y;

    public MenuButton(int x, int y, String type) {

        this.x = x;
        this.y = y;
        String direction = "../img/" + type + "ButtonNoPush.png";
        String direction2 = "../img/" + type + "ButtonPush.png";
        String direction3 = "../img/" + type + "Button.png";
        try {
            url = this.getClass().getResource(direction);
            noSelected = ImageIO.read(url);
            this.bffimg = noSelected;
            
            url = this.getClass().getResource(direction2);
            selected = ImageIO.read(url);
            
            url = this.getClass().getResource(direction3);
            nothing = ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(MenuButton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    public BufferedImage getBffImage(){
        return this.bffimg;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void aimOver(){
        this.bffimg = this.nothing;
    }
    
    public void nothingOver(){
        this.bffimg = this.noSelected;
    }
    public void select(){
        this.bffimg = this.selected;
    }
}

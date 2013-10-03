package spaceconquest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luis Santiago
 */
public class Scenario_test extends Canvas implements Runnable{
    
    private Image background;
    private Graphics bufferGraphics; 
    private BufferedImage bufferImg;
    private final int width = this.getWidth();
    private final int height = this.getHeight();
    private boolean isPlaying = true;
    private Aimer aim1;
    
    public Scenario_test(){
        aim1 = new Aimer(200,200,1);
        setBackground(new Color(255,255,255));
    }
    
    public void paint(Graphics g){
        g.drawImage(aim1.bffimg, aim1.getX(), aim1.getY(), null);
    }

    @Override
    public void run() {
        try{
            Thread.sleep(100);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        while(true){
            
        }
    }
    
}
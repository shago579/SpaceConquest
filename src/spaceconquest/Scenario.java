package spaceconquest;

import java.awt.Canvas;
import java.awt.Color;


public class Scenario extends Canvas implements Runnable{
	
    private boolean is_playing = true;
    
    public Scenario(){
        setBackground(new Color(255,255,255));
    }
    
    public void run(){
        
        while(is_playing){
            try{
                Thread.sleep(1000 / 60);
                repaint();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }
}

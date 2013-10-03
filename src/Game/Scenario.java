package Game;

import Xbox.ButtonListener;
import Xbox.LeftAxisListener;
import Xbox.RightAxisListener;
import Xbox.XboxController;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import spaceconquest.Aimer;

/**
 *
 * @author Luis Santiago
 */
public class Scenario extends Canvas implements Runnable{
    
    private Image background;
    private Graphics bufferGraphics; 
    private BufferedImage bufferImg;
    private final int width = this.getWidth();
    private final int height = this.getHeight();
    private boolean isPlaying = true;
    private boolean firstTime = true;
    private Spaceman playerOne;
    private Aimer aim1;
    private XboxController xbox1;
     
    public Scenario() throws IOException{
        this.bufferImg = new BufferedImage(570, 380, BufferedImage.TYPE_INT_RGB);
        this.background = new ImageIcon(getClass().getClassLoader().getResource("img/fondo.png")).getImage();
        this.bufferGraphics = this.bufferImg.createGraphics();
        aim1 = new Aimer(200,200,1);
        playerOne = new Spaceman(30,640,this.getWidth()/2);
        xbox1 = new XboxController();
        initControls();
        Thread t1 = new Thread(xbox1);
        t1.start();
        playerOne.weapon.update_angle(aim1.getX() - playerOne.get_x() , aim1.getY() - (playerOne.get_y() + 60));
    }
    
    @Override
    public void paint(Graphics g){
        update(g);    
    }
    @Override
    public void update(Graphics g){
         if (firstTime) {
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            this.bufferImg = (BufferedImage) createImage(w, h);
            this.bufferGraphics = this.bufferImg.createGraphics();
            firstTime = false;
        }
        bufferGraphics.drawImage(this.background, -1, 0,this.getWidth()+1,this.getHeight(), this);
        bufferGraphics.drawImage(aim1.bffimg, aim1.getX(), aim1.getY(), null);
        bufferGraphics.drawImage(this.playerOne.img,this.playerOne.get_x(),this.playerOne.get_y(),this);
        
        bufferGraphics.drawImage(this.playerOne.weapon.img,this.playerOne.weapon.get_x(),this.playerOne.weapon.get_y(),this);
        g.drawImage(this.bufferImg,0,0,this);
    }

    @Override
    public void run() {
        
        while(isPlaying){
            
            try{
                repaint();
                Thread.sleep(1000 / 60);
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
     private void initControls() {

        xbox1.addRightAxisListener(new RightAxisListener() {
            public void rightAxisMoveVertical(float movement) {
                if (movement > 0) {
                    aim1.setY(aim1.getY() + 20);
                } else if(movement < 0){
                    aim1.setY(aim1.getY() - 20);
                }
                try {
                    playerOne.weapon.update_angle(aim1.getX() - playerOne.get_x() , aim1.getY() - (playerOne.get_y() + 60));
                } catch (IOException ex) {
                    Logger.getLogger(Scenario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            public void rightAxisMoveHorizontal(float movement) {
                if (movement > 0) {
                    aim1.setX(aim1.getX() + 20 );
                } else if(movement < 0){
                    aim1.setX(aim1.getX() - 20);
                }
                try {
                    playerOne.weapon.update_angle(aim1.getX() - playerOne.get_x() , aim1.getY() - (playerOne.get_y() + 60));
                } catch (IOException ex) {
                    Logger.getLogger(Scenario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        xbox1.addLeftAxisListener(new LeftAxisListener() {
            public void leftAxisMoveVertical(float movement) {
                
            }

            public void leftAxisMoveHorizontal(float movement) {
                if (movement > 0) {
                    playerOne.move_right();
                } else if(movement < 0){
                    playerOne.move_left();
                }
            }
        });

        xbox1.addButtonListener(new ButtonListener() {
            @Override
            public void aButtonPressed() {
                //System.out.println("Click");
                /*
                if(aimOver(aim1, startbtn)){
    
                    
                }
                if(aimOver(aim1, exitbtn))
                    System.exit(0);
                    //myFrame.dispose();
                    * */
            }

            public void bButtonPressed() {
            }

            public void xButtonPressed() {
            }

            public void yButtonPressed() {
            }

            public void lbButtonPressed() {
            }

            public void rbButtonPressed() {
            }

            public void backButtonPressed() {
            }

            public void startButtonPressed() {
            }

            public void leftStickButtonPressed() {
            }

            public void rightStickButtonPressed() {
            }
        });
    }
}

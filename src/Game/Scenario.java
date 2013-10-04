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
import java.util.ArrayList;
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
    private final int width;
    private final int height;
    private boolean isPlaying = true;
    private boolean firstTime = true;
    private Spaceman playerOne;
    private Alien playerTwo;
    private Aimer aim1;
    private XboxController xbox1;
    private ArrayList<Platform> platforms;
    /**
     *
     * @param level
     * @param width
     * @param height
     * @throws IOException
     */
    public Scenario(int level,int width,int height) throws IOException{
        this.width = width;
        this.height = height;
        this.bufferImg = new BufferedImage(570, 380, BufferedImage.TYPE_INT_RGB);
        this.background = new ImageIcon(getClass().getClassLoader().getResource("img/fondo.png")).getImage();
        this.bufferGraphics = this.bufferImg.createGraphics();
        aim1 = new Aimer(200,200,1);
        playerOne = new Spaceman(30,660,width,height);
        Thread t1 = new Thread(playerOne);
        t1.start();
        playerTwo = new Alien(width-100,645,width,height);
        xbox1 = new XboxController();
        initControls();
        t1 = new Thread(xbox1);
        t1.start();
        playerOne.weapon.update_angle(aim1.getX() - playerOne.get_x() , aim1.getY() - (playerOne.get_y() + 60));
        setLevel(level);
        
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
        bufferGraphics.drawImage(this.background, -1, 0,1500,this.height, this);
        bufferGraphics.drawImage(aim1.bffimg, aim1.getX(), aim1.getY(), null);
        bufferGraphics.drawImage(this.playerOne.img,this.playerOne.get_x(),this.playerOne.get_y(),this.playerOne.get_width(),this.playerOne.get_height(),this);
        bufferGraphics.drawImage(this.playerTwo.img,this.playerTwo.get_x(),this.playerTwo.get_y(),this.playerTwo.get_width(),this.playerTwo.get_height(),this);
        for(Platform p : platforms){
            bufferGraphics.drawImage(p.img,p.get_x(),p.get_y(),p.get_width(),p.get_height(),this);
            checkPlatformCollision(p);
        }
        for(Bullet b : this.playerOne.bullets){
            bufferGraphics.drawImage(b.img,b.get_x(),b.get_y(),this);
            checkCollisions(b);
        }
        bufferGraphics.drawImage(this.playerOne.weapon.img,this.playerOne.weapon.get_x(),this.playerOne.weapon.get_y(),this);
        bufferGraphics.drawImage(this.playerTwo.weapon.img,this.playerTwo.weapon.get_x(),this.playerTwo.weapon.get_y(),this);
        g.drawImage(this.bufferImg,0,0,this);
    }
    public void checkCollisions(Bullet b){
        for(Platform p : this.platforms){
            if(collision(b,p)){
                b.hit();
            }
        }
        
         if(b.weapon.character.getClass().getName().equals("Game.Spaceman") ){
             
             if(collision(b,playerTwo)){
                 int current_life_playerTwo = playerTwo.hurt(10,b);
                 b.hit();
                 
             }
         }
         else{
             
         }
    }
    public void checkPlatformCollision(Platform p){
        /*
         * personaje.contador= 0;
         * personaje.setY(plataforma.getY() - personaje.getHeight());
         * personaje.vy *= -val_reb;	
         */
        if(collision(p,playerOne)){
            
            if(playerOne.get_y() < ( p.get_y())){
                System.out.println(":P");
                System.out.println(p.get_y() - playerOne.get_height() +5);
                playerOne.set_y(p.get_y() - playerOne.get_height() +5);
                playerOne.y_speed *= 0;
            }
            else if(playerOne.get_x()>(p.get_x()-playerOne.get_width()) && playerOne.get_y() > p.get_y()){
                playerOne.set_x(p.get_x() - 10 - playerOne.get_width());
            }
            else if(playerOne.get_x()<(p.get_x()-playerOne.get_width()) && playerOne.get_y() > p.get_y() ){
                playerOne.set_x(p.get_x() + 5 + p.get_width());
            }
            
                
        }
    }
    public boolean collision(Collisionable b, Collisionable a){
	boolean hit = false;
	//Colsiones horizontales
	if(b.get_x() + b.get_width() >= a.get_x() && b.get_x() < a.get_x() + a.get_width())
	{
		//Colisiones verticales
		if(b.get_y() + b.get_height() >= a.get_y() && b.get_y() < a.get_y() + a.get_height())
			hit = true;
	}
	//Colisión de a con b
	if(b.get_x() <= a.get_x() && b.get_x() + b.get_width() >= a.get_x() + a.get_width())
	{
		if(b.get_y() <= a.get_y() && b.get_y() + b.get_height() >= a.get_y() + a.get_height())
			hit = true;
	}
	//Colisión b con a
	if(a.get_x() <= b.get_x() && a.get_x() + a.get_width() >= b.get_x() + b.get_width())
	{
		if(a.get_y() <= b.get_y() && a.get_y() + a.get_height() >= b.get_y() + b.get_height())
			hit = true;
	}
	return hit;
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
               int x = aim1.getX() - playerOne.get_x();
               int y = aim1.getY() - (playerOne.get_y());
               playerOne.shoot(y/20,x/20);
            }

            public void bButtonPressed() {
                playerOne.jump();
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
     private void setLevel(int level){
         this.platforms = new ArrayList<Platform>();
         if(level == 1){
             this.platforms.add(new Platform((this.width/2)-30,this.height- (342),40,300));
         }
         if(level == 2){
             this.platforms.add(new Platform((this.width/2)-30,this.height- (325),40,300));
             this.platforms.add(new Platform((this.width/2)+250,this.height- (425),40,400));
             this.platforms.add(new Platform((this.width/2)-250,this.height- (425),40,400));
         }
         if(level == 1){
             this.platforms.add(new Platform((this.width/2)-30,this.height- (332),40,300));
         }
     }
    
}

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
    private Aimer aim2;
    public int winner = 0;
    private XboxController xbox1;
    private XboxController xbox2;
    private ArrayList<Platform> platforms;
    private Audio audio;
    private boolean a_is_pressed = false;
    private boolean b_is_pressed = false;
    private boolean a2_is_pressed = false;
    private boolean b2_is_pressed = false;
    /**
     *
     * @param level
     * @param width
     * @param height
     * @throws IOException
     */
    public Scenario(int level,int width,int height) throws IOException{
        /*
         * Define the necessary variables, such as the player, the size of the window,
         * the xbox controls, the graphics buffer and so on.
         */
        this.width = width;
        this.height = height;
        this.bufferImg = new BufferedImage(570, 380, BufferedImage.TYPE_INT_RGB);
        this.background = new ImageIcon(getClass().getClassLoader().getResource("img/fondo.png")).getImage();
        this.bufferGraphics = this.bufferImg.createGraphics();
        aim1 = new Aimer(200,200,1);
        aim2 = new Aimer(400,200,1);
        playerOne = new Spaceman(30,660,width,height);
        Thread t1 = new Thread(playerOne);
        t1.start();
        playerTwo = new Alien(width-100,645,width,height);
        Thread t2 = new Thread(playerTwo);
        t2.start();
        xbox1 = new XboxController(0);
        xbox2 = new XboxController(1);
        initControls();
        t1 = new Thread(xbox1);
        t1.start();
        t1 = new Thread(xbox2);
        t1.start();
        //We update the angle of the weapon of each character
        playerOne.weapon.update_angle(aim1.getX() - playerOne.get_x() , aim1.getY() - (playerOne.get_y() + 60));
        playerTwo.weapon.update_angle(aim2.getX() - playerTwo.get_x() , aim2.getY() - (playerTwo.get_y() + 60));
        //In this line we define the level of the game and build the corresponding platforms
        setLevel(level);
        audio = new Audio();
        
    }
    
    @Override
    public void paint(Graphics g){
        // Update the graphics of the Canvas.
        update(g);    
    }
    @Override
    public void update(Graphics g){
        /*
         * If it's the first time of the update, we build the graphics buffer.
         */
         if (firstTime) {
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            this.bufferImg = (BufferedImage) createImage(w, h);
            this.bufferGraphics = this.bufferImg.createGraphics();
            firstTime = false;
        }
         //Draw the background
        bufferGraphics.drawImage(this.background, -1, 0,1500,this.height, this);
        /*
         * If there is a winner, show it.
         */
        if(winner == 1){
            bufferGraphics.drawImage(playerOne.winner_image, this.width/2 - 400, this.height/2 -300,this);
            g.drawImage(this.bufferImg,0,0,this);
            return;
        }
        if(winner == 2){
            bufferGraphics.drawImage(playerTwo.winner_image, this.width/2 -300, this.height/2 - 300,this);
            g.drawImage(this.bufferImg,0,0,this);
            return;
        }
        
        /*
         * Draw the characters of the game and the life.
         */
        bufferGraphics.drawImage(this.playerOne.img,this.playerOne.get_x(),this.playerOne.get_y(),this.playerOne.get_width(),this.playerOne.get_height(),this);
        bufferGraphics.drawImage(this.playerOne.get_image_life(),30,20,this);
        bufferGraphics.drawImage(this.playerTwo.get_image_life(),1000,20,this);
        bufferGraphics.drawImage(this.playerTwo.img,this.playerTwo.get_x(),this.playerTwo.get_y(),this.playerTwo.get_width(),this.playerTwo.get_height(),this);
        /*
         * Draw each platforms and check for collisions with the players
         */
        for(Platform p : platforms){
            bufferGraphics.drawImage(p.img,p.get_x(),p.get_y(),p.get_width(),p.get_height(),this);
            checkPlatformCollision(p);
        }
        /*
         * Draw each bullet and check for collisions with the players and the platforms of the player one
         */
        for(Bullet b : this.playerOne.bullets){
            if(b.isInterrupted()){
                continue;
            }
            bufferGraphics.drawImage(b.img,b.get_x(),b.get_y(),this);
            checkCollisions(b);
        }
        // Now we check with the bullets of the Alien
         for(Bullet b : this.playerTwo.bullets){
            if(b.isInterrupted()){
                continue;
            }
            bufferGraphics.drawImage(b.img,b.get_x(),b.get_y(),this);
            checkCollisions(b);
        }
         /*
         * Draw the weapons
         */
        bufferGraphics.drawImage(this.playerOne.weapon.img,this.playerOne.weapon.get_x(),this.playerOne.weapon.get_y(),this);
        bufferGraphics.drawImage(this.playerTwo.weapon.img,this.playerTwo.weapon.get_x(),this.playerTwo.weapon.get_y(),this);
        /*
         * Draw the graphics of the buffer in the Canvas
         */
        g.drawImage(this.bufferImg,0,0,this);
    }
    public void checkCollisions(Bullet b){
        /*
         * Check for collisions with the bullets
         */
         if(b.isInterrupted()){
            b.hit();
        } 
        if(b.is_alive){
        for(Platform p : this.platforms){
            //If the bullets collides with a platform, we destroy it
            if(collision(b,p)){
                b.is_alive = false;
            }
        }
        
         if(b.weapon.character.getClass().getName().equals("Game.Spaceman") ){
             // If it collides with the other player, we extract 20 points from the life
             if(collision(b,playerTwo)){
                 int current_life_playerTwo = playerTwo.hurt(20,b);
                 if(current_life_playerTwo == 0){
                     this.winner = 1;
                 }
                 b.is_alive = false;
                 
             }
         }
         else if(b.weapon.character.getClass().getName().equals("Game.Alien") ){
             // If it collides with the other player, we extract 20 points from the life
             if(collision(b,playerOne)){
                 int current_life_playerOne = playerOne.hurt(20,b);
                 if(current_life_playerOne == 0){
                     this.winner = 2;
                 }
                 b.is_alive = false;
             }
         }
        }
        // The next lines are just to avoid a Concurrent Modification error
        else if(!b.is_alive && !b.isInterrupted()){
            b.hit();
        }
        else{
            b.interrupt();
        }
    }
    public void checkPlatformCollision(Platform p){
        /*
         * Check for collides between the platforms and the players	
         */
        if(collision(p,playerOne)){
            
            if(playerOne.get_y() < ( p.get_y())){
                // Ih the collision is from above, the character stays over the platform
                playerOne.set_y(p.get_y() - playerOne.get_height() +5);
                playerOne.y_speed *= 0;
            }
            else if(playerOne.get_x()- 10 < (p.get_x())){
                // Ih the collision is from the left, the character stays at the left of the platform
                playerOne.set_x(p.get_x() - 10 - playerOne.get_width());
            }
            else if((playerOne.get_x() + 10)>(p.get_x()) ){
                // Ih the collision is from the right, the character stays at the right of the platform
                playerOne.set_x(playerOne.get_x() + 20); 
            }              
        }
        if(collision(p,playerTwo)){
            
            if(playerTwo.get_y() < ( p.get_y())){
                playerTwo.set_y(p.get_y() - playerTwo.get_height() +5);
                playerTwo.y_speed *= 0;
            }
            else if(playerTwo.get_x()- 10 < (p.get_x())){
                playerTwo.set_x(p.get_x() - 10 - playerTwo.get_width());
            }
            else if((playerTwo.get_x() + 10)>(p.get_x()) ){
                
                playerTwo.set_x(playerTwo.get_x() + 20); 
            }
        }
    }
    public boolean collision(Collisionable b, Collisionable a){
	boolean hit = false;
	//Horizontal collides
	if(b.get_x() + b.get_width() >= a.get_x() && b.get_x() < a.get_x() + a.get_width())
	{
		//Vertical collides
		if(b.get_y() + b.get_height() >= a.get_y() && b.get_y() < a.get_y() + a.get_height())
			hit = true;
	}
	//When a collides with b
	if(b.get_x() <= a.get_x() && b.get_x() + b.get_width() >= a.get_x() + a.get_width())
	{
		if(b.get_y() <= a.get_y() && b.get_y() + b.get_height() >= a.get_y() + a.get_height())
			hit = true;
	}
	//When b collides with a
	if(a.get_x() <= b.get_x() && a.get_x() + a.get_width() >= b.get_x() + b.get_width())
	{
		if(a.get_y() <= b.get_y() && a.get_y() + a.get_height() >= b.get_y() + b.get_height())
			hit = true;
	}
	return hit;
    }
    @Override
    public void run() {
        //This is the main thread
        while(isPlaying){
            
            try{
                repaint();
                /*
                 * We check if the a and b buttons are pressed or not.
                 */
                if(!xbox1.controller.isButtonPressed(0)){
                    a_is_pressed = false;
                }
                if(!xbox1.controller.isButtonPressed(1)){
                    b_is_pressed = false;
                }
                if(!xbox2.controller.isButtonPressed(0)){
                    a2_is_pressed = false;
                }
                if(!xbox2.controller.isButtonPressed(1)){
                    b2_is_pressed = false;
                }
                //Sleeps the thread for a while in order to act like a frame timeline.
                Thread.sleep(1000 / 60);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
     private void initControls() {
         /*
          * Set ups the listeners for the control of the Spaceman
          */
        xbox1.addRightAxisListener(new RightAxisListener() {
            /*
             * Updates the prompter of the Spaceman's weapon
             */
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
                 /*
                * Updates the prompter of the Spaceman's weapon
                */
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
                /*
                 * Moves the spaceman
                 */
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
                // Shoot a note
               if(!a_is_pressed){
                   int x = aim1.getX() - playerOne.get_x();
                    int y = aim1.getY() - (playerOne.get_y());
                    playerOne.shoot(y/20,10);
                    audio.play();
               }
               a_is_pressed = true;
               
            }

            public void bButtonPressed() {
                // The spaceman jumps
                if(!b_is_pressed){
                   playerOne.jump();
               }
                b_is_pressed = true;
                
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
        /*
          * Set ups the listeners for the control of the Alien
          */
        xbox2.addRightAxisListener(new RightAxisListener() {
            /*
             * Updates the prompter of the Alien's weapon
             */
            public void rightAxisMoveVertical(float movement) {
                if (movement > 0) {
                    aim2.setY(aim2.getY() + 20);
                } else if(movement < 0){
                    aim2.setY(aim2.getY() - 20);
                }
                try {
                    playerTwo.weapon.update_angle(aim2.getX() - playerTwo.get_x() , aim2.getY() - (playerTwo.get_y() + 60));
                } catch (IOException ex) {
                    Logger.getLogger(Scenario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            public void rightAxisMoveHorizontal(float movement) {
                /*
             * Updates the prompter of the Alien's weapon
             */
                if (movement > 0) {
                    aim2.setX(aim2.getX() + 20 );
                } else if(movement < 0){
                    aim2.setX(aim2.getX() - 20);
                }
                try {
                    playerTwo.weapon.update_angle(aim2.getX() - playerTwo.get_x() , aim2.getY() - (playerTwo.get_y() + 60));
                } catch (IOException ex) {
                    Logger.getLogger(Scenario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        xbox2.addLeftAxisListener(new LeftAxisListener() {
            public void leftAxisMoveVertical(float movement) {
                
            }

            public void leftAxisMoveHorizontal(float movement) {
                //Moves the Alien
                if (movement > 0) {
                    playerTwo.move_right();
                } else if(movement < 0){
                    playerTwo.move_left();
                }
            }
        });

        xbox2.addButtonListener(new ButtonListener() {
            @Override
            public void aButtonPressed() {
                //Shoot a note
               if(!a2_is_pressed){
                   int x = aim2.getX() - playerTwo.get_x();
                   
                    int y = aim2.getY() - (playerTwo.get_y());
                    
                    playerTwo.shoot(y/20,-10);
                    audio.play();
               }
               a2_is_pressed = true;
               
            }

            public void bButtonPressed() {
                // The Alien jumps
                if(!b2_is_pressed){
                   playerTwo.jump();
               }
                b2_is_pressed = true;
                
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
         /*
          * Set the level of the Game and build the necessary platforms
          */
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

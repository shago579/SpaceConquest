package spaceconquest;

import Xbox.ButtonListener;
import Xbox.LeftAxisListener;
import Xbox.RightAxisListener;
import Xbox.TriggersListener;
import Xbox.XboxController;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Credits extends Canvas implements Runnable{
    
    private URL url;
    private BufferedImage bffimg;
    private MenuButton back;
    private Aimer aim;
    private Xbox.XboxController xbox;
    private MainFrame frame;
    private BufferedImage bufferImg;
    private Graphics bufferGraphics;
    private Image img;
    public Credits(MainFrame frame){
        Thread this_t = new Thread(this) ;
        this_t.start();
        this.frame = frame;
        this.bufferImg = new BufferedImage(570, 380, BufferedImage.TYPE_INT_RGB);
        this.bufferGraphics = this.bufferImg.createGraphics();
        this.img = new ImageIcon(getClass().getClassLoader().getResource("img/Nombres.png")).getImage();
        aim = new Aimer(500, 100, 2);
        back = new MenuButton(0,600,"Back");
        xbox = new XboxController(0);
        Thread t = new Thread(xbox);
        initControls();
        t.start();
        String direction = "../img/UPLogo.png";
        try {
            url = this.getClass().getResource(direction);
            bffimg = ImageIO.read(url);
            
        } catch (IOException ex) {
            Logger.getLogger(MenuButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.black);
    }
    private boolean firstTime = true;
    
    public void paint(Graphics g){
        update(g);
    }
    
    public void update(Graphics g){
        if (firstTime) {
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            this.bufferImg = (BufferedImage) createImage(w, h);
            this.bufferGraphics = this.bufferImg.createGraphics();
            firstTime = false;
        }
        bufferGraphics.fillRect( 0, 0, 1700,1000);
        if(aimOver(aim, back))
            back.aimOver();
        else
            back.nothingOver();
        
        bufferGraphics.drawImage(bffimg, 50, 50, null);
        bufferGraphics.drawImage(this.img, 600,300, null);
        g.drawImage(this.bufferImg,0,0,this);
    }
    
    
    private void initControls() {

        xbox.addRightAxisListener(new RightAxisListener() {
            public void rightAxisMoveVertical(float movement) {
                
            }

            public void rightAxisMoveHorizontal(float movement) {
                
            }
        });
        
        xbox.addTriggersListener(new TriggersListener(){

            @Override
            public void rightTriggerPressed(boolean press) {
                 System.out.println(":(");
                backMenu();
            }

            @Override
            public void leftTriggerPressed(boolean press) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });

        xbox.addLeftAxisListener(new LeftAxisListener() {
            public void leftAxisMoveVertical(float movement) {
            }

            public void leftAxisMoveHorizontal(float movement) {
            }
        });

        xbox.addButtonListener(new ButtonListener() {
            @Override
            public void aButtonPressed() {
               
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
    
    public void backMenu(){
        frame.backToMenu(this);
    }
    public boolean aimOver(Aimer b, MenuButton a){
	boolean hit = false;
	//Colsiones horizontales
	if(b.getX() + b.getWidth() >= a.getX() && b.getX() < a.getX() + a.getWidth())
	{
		//Colisiones verticales
		if(b.getY() + b.getHeight() >= a.getY() && b.getY() < a.getY() + a.getHeight())
			hit = true;
	}
	//Colisión de a con b
	if(b.getX() <= a.getX() && b.getX() + b.getWidth() >= a.getX() + a.getWidth())
	{
		if(b.getY() <= a.getY() && b.getY() + b.getHeight() >= a.getY() + a.getHeight())
			hit = true;
	}
	//Colisión b con a
	if(a.getX() <= b.getX() && a.getX() + a.getWidth() >= b.getX() + b.getWidth())
	{
		if(a.getY() <= b.getY() && a.getY() + a.getHeight() >= b.getY() + b.getHeight())
			hit = true;
	}
	return hit;
    }

    @Override
    public void run() {
         //This is the main thread
            
            try{
                while(true){
                    repaint();
                
                    Thread.sleep(1000 / 60);
                }
                
            }catch(Exception e){
                
            }
    }
  
}

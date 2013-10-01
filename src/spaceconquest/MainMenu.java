package spaceconquest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;

import Xbox.*;
import java.awt.Canvas;

public class MainMenu extends Canvas implements Runnable{

    private MenuButton startbtn;
    private MenuButton exitbtn;
    private JLabel title;
    private MainFrame myFrame;
    private XboxController xbox1;
    private boolean firstTime = true;
    private Graphics bufferGraphics;
    private BufferedImage bufferImg;
    private Aimer aim1;
    
    private boolean isOver;

    public MainMenu(MainFrame myFrame) {

        this.bufferImg = new BufferedImage(570, 380, BufferedImage.TYPE_INT_RGB);
        this.bufferGraphics = this.bufferImg.createGraphics();

        this.myFrame = myFrame;
        this.aim1 = new Aimer(100, 150, 1);

        xbox1 = new XboxController();
        initControls();
        Thread t1 = new Thread(xbox1);
        t1.start();

        startbtn = new MenuButton(550, 300, "Start");
        exitbtn = new MenuButton(550, 450, "Exit");
        title = new JLabel("Titulo del Juego");

        title.setSize(200, 150);
        title.setPreferredSize(new Dimension(200, 250));
        title.setMaximumSize(new Dimension(200, 250));
        title.setMinimumSize(new Dimension(200, 250));
        title.setBackground(new Color(0, 0, 0));

        setBackground(new Color(0, 0, 0));

        ExecutorService executor = Executors.newFixedThreadPool(15);
        for (int count = 1; count < 150; count++) {
            //executor.submit(new Processor(i));
        }
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void update(Graphics g) {
        if (firstTime) {
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            this.bufferImg = (BufferedImage) createImage(w, h);
            this.bufferGraphics = this.bufferImg.createGraphics();
            firstTime = false;
        }
        g.drawImage(this.bufferImg, 0, 0, this);
        if(aimOver(aim1, startbtn))
            startbtn.aimOver();
        else
            startbtn.nothingOver();
        if(aimOver(aim1,exitbtn))
            exitbtn.aimOver();
        else
            exitbtn.nothingOver();
        
        g.drawImage(startbtn.getBffImage(), startbtn.getX(), startbtn.getY(), title);
        g.drawImage(exitbtn.getBffImage(), exitbtn.getX(), exitbtn.getY(), title);
        g.drawImage(aim1.bffimg, aim1.getX(), aim1.getY(), null); // see javadoc for more info on the parameters 
        g.drawImage(this.aim1.bffimg, this.aim1.x, this.aim1.y, this);
        
    }

    public void initControls() {

        xbox1.addRightAxisListener(new RightAxisListener() {
            public void rightAxisMoveVertical(float movement) {
                if (movement > 0) {
                    aim1.setY(aim1.getY() + 20);
                } else if(movement < 0){
                    aim1.setY(aim1.getY() - 20);
                }
                repaint();
            }

            public void rightAxisMoveHorizontal(float movement) {
                if (movement > 0) {
                    aim1.setX(aim1.getX() + 20 );
                } else if(movement < 0){
                    aim1.setX(aim1.getX() - 20);
                }
                repaint();
            }
        });

        xbox1.addLeftAxisListener(new LeftAxisListener() {
            public void leftAxisMoveVertical(float movement) {
            }

            public void leftAxisMoveHorizontal(float movement) {
            }
        });

        xbox1.addButtonListener(new ButtonListener() {
            @Override
            public void aButtonPressed() {
                //System.out.println("Click");
                if(aimOver(aim1, startbtn)){
                    
                }
                if(aimOver(aim1, exitbtn))
                    myFrame.dispose();
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
        while (isOver){
        
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

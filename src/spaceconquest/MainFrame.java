package spaceconquest;

import javax.swing.JFrame;
import Game.Scenario;
import java.io.IOException;

public class MainFrame extends JFrame {
    /*
     * Interface for initialize the game
     */
    private MainMenu menu;
    private Scenario scene1;
    private Credits credit;
    

    public MainFrame() {
        /*
     *  Creates a new Menu
        */
        menu = new MainMenu(this);

        setResizable(false);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add();
        add(menu);

        
        setVisible(true);
    }
    public void backToMenu(Credits c){
        /*
     * Returns to the menu coming from the credits
     */
        
        this.remove(c);
        menu = new MainMenu(this);
        this.add(menu);
        setVisible(true);
        repaint();
    }
    public void startGame(int level) throws IOException {
        /*
     * Creates a new Scenario object that starts the game when running the thread
     */
        this.menu.xbox1.finish();
        scene1 = new Scenario(level,this.getWidth(),this.getHeight());
        scene1.setBounds(0,0, this.getWidth(),this.getHeight());
        Thread tgame = new Thread(scene1);
        add(scene1);
        remove(menu);        
        tgame.start();
        repaint();
    }
     public void showCredits(){
         /*
     * Displays the credits of the game
     */
        credit = new Credits(this);
        credit.setBounds(0,0, this.getWidth(),this.getHeight());
        add(credit);
        repaint();
        remove(menu);        
        repaint();
    }

}

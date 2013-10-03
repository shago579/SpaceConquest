package spaceconquest;

import javax.swing.JFrame;
//import Game.Scenario;

public class MainFrame extends JFrame {

    private MainMenu menu;
    private Scenario scene1;
    //private Scenario level1;

    public MainFrame() {

        menu = new MainMenu(this);
        //scene1 = new Scenario();

        setResizable(false);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add();
        add(menu);

        
        setVisible(true);
    }

    public void startGame() {
        scene1 = new Scenario();
        Thread tgame = new Thread(scene1);
        add(scene1);
        menu.isOver = false;
        remove(menu);
        
        
        tgame.start();
        //repaint();
    }
}

package spaceconquest;

import javax.swing.JFrame;


public class MainFrame extends JFrame{
	
	private MainMenu menu;
	private Scenario scene1;
	
	public MainFrame(){
		
		menu = new MainMenu(this);
		scene1 = new Scenario();
		
		setResizable(false);
		setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add();
		add(menu);
		
		
		setVisible(true);
	}
	
	public void startGame(){
		remove(menu);
		add(scene1);
		repaint();
	}
}

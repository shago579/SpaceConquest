package spaceconquest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainMenu extends JPanel implements ActionListener{
	private MenuButton startbtn;
	private MenuButton exitbtn;
	private JLabel title;
	private MainFrame myFrame;
	
	public MainMenu(MainFrame myFrame){
		this.myFrame = myFrame;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		startbtn = new MenuButton("Start");
		exitbtn = new MenuButton("Exit");
		title = new JLabel("Titulo del Juego");
		
		
		
		title.setSize(200, 150);
		title.setPreferredSize(new Dimension(200,250));
		title.setMaximumSize(new Dimension(200,250));
		title.setMinimumSize(new Dimension(200,250));
		title.setBackground(new Color(0,0,0));
		
		
		startbtn.addActionListener(this);
		exitbtn.addActionListener(this);
		
		setBackground(new Color(0,0,0));
		add(title);
		add(startbtn);
		add(exitbtn);
		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		startbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		ExecutorService executor = Executors.newFixedThreadPool(15);
		for(int count = 1; count < 150; count++){
			//executor.submit(new Processor(i));
		}
	}

	public void actionPerformed(ActionEvent e) {
		MenuButton clicked = (MenuButton) e.getSource();
		
		if(clicked == startbtn){
			myFrame.startGame();
		}else if(clicked == exitbtn){
			myFrame.dispose();
		}
	}
}

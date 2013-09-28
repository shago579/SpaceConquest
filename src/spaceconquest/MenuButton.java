package spaceconquest;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuButton extends JButton implements MouseListener{
	
	private int width = 275;
	private int height = 75;
	private String url;
	public MenuButton(String url) {
		
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		
		this.url = url;
	    setPreferredSize(new Dimension(width, height));
	    setMaximumSize(new Dimension(width, height));
	    setFocusPainted(false);
	    setRolloverEnabled(false);
	    setOpaque(false);
	    setContentAreaFilled(false);
	    setBorderPainted(false);
	    setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); 
	    
	    addMouseListener(this);
	    String direction = "../img/"+url+"ButtonNoPush.png";
	    setIcon(new ImageIcon(MainMenu.class.getResource(direction)));
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		String direction = "../img/"+url+"ButtonPush.png";
		setIcon(new ImageIcon(MainMenu.class.getResource(direction)));
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		String direction = "../img/"+url+"Button.png";
		setIcon(new ImageIcon(MainMenu.class.getResource(direction)));
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		String direction = "../img/"+url+"ButtonNoPush.png";
		setIcon(new ImageIcon(MainMenu.class.getResource(direction)));
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		String direction = "../img/"+url+"ButtonPush.png";
		setIcon(new ImageIcon(MainMenu.class.getResource(direction)));
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}

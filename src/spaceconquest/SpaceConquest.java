package spaceconquest;

import javax.swing.SwingUtilities;

/**
 *
 * @author Luis Santiago
 */
public class SpaceConquest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MainFrame();
            }
        });
    }
}

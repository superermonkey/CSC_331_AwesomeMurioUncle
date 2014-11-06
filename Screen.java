import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Screen extends JPanel{
	public static int screenWidth = 700;
	public static int screenHeight = 500;
	public static ImageIcon backgroundImg = new ImageIcon("/img/happy_background.jpg");
	
	private ArrayList<LevelObject> objects;
	private javax.swing.Timer timer;
	
	public Screen() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.blue);
		
		//Array to hold all the objects in the level.
		objects = new ArrayList<LevelObject>();
		
		
		
		
		
		//Start level timer at window creation.
		//timer.start();
		
	}
	
	
	public void paintComponent(Graphics2D g2) {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		
		super.paintComponent(g2);
		g2.drawImage(backgroundImg.getImage(), 
				screenWidth, screenHeight, null);
	}
}

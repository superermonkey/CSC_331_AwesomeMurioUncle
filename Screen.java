import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

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
	public static ImageIcon backgroundImg = new ImageIcon("img/happy_background.jpg");
	public static ImageIcon marioImg = new ImageIcon("img/Mario_walk.gif");
	
	private ArrayList<LevelObject> objects;
	private javax.swing.Timer timer;
	
	public Screen() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.blue);
		
		//Array to hold all the objects in the level.
		objects = new ArrayList<LevelObject>();
		
		// Create Player
		Point playerLocation = new Point(50,200);
		Dimension playerSize = new Dimension(50,30);
		boolean playerVisibility = true;
		Vector playerVelocity = new Vector(0,0);
		
		
		Player player = new Player(playerLocation, playerSize, playerVisibility, playerVelocity);
		
		objects.add(player);
		
		
		//Start level timer at window creation.
		//timer.start();
		
	}
	
	
	public void paintComponent(Graphics g) {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		super.paintComponent(g);
		
		g.drawImage(backgroundImg.getImage(), 0, 0, screenWidth, screenHeight, null);
		// draw objects
		for (LevelObject obj : objects) {
			obj.draw(g);
		}
	}
}

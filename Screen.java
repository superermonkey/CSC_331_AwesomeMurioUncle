import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Screen extends JPanel implements KeyListener{
	public static int screenWidth = 700;
	public static int screenHeight = 500;
	public static ImageIcon backgroundImg = new ImageIcon("img/happy_background.jpg");
	public static ImageIcon playerImg = new ImageIcon("img/Mario_walk.gif");
	
	private ArrayList<Actor> actors;
	private ArrayList<LevelObject> objects;
	private Timer timer;
	
	private Player player;
	private Ground ground;
	
	public Screen() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.blue);
		setFocusable(true);
		requestFocusInWindow();
		
		//Array to hold all the actors in the level.
		actors = new ArrayList<Actor>();
		// Array to hold all of the objects in the level, including stationary ones like ground and boxes.
		objects = new ArrayList<LevelObject>();
		
		// Create Player
		Point playerLocation = new Point(50,200);
		Dimension playerSize = new Dimension(30,50);
		boolean playerVisibility = true;
		Vector playerVelocity = new Vector(0,0);
		player = new Player(playerLocation, playerSize, playerVisibility, playerVelocity, playerImg.getImage());
		
		
		// Create Ground
		Point groundLocation = new Point(0, 450);
		Dimension groundSize = new Dimension(100, 11);
		boolean groundVisibility = true;
		ground = new Ground(groundLocation, groundSize, groundVisibility, playerImg.getImage());
		ground.buildGround((int)groundSize.width/10);
		
		objects.add(ground);
		actors.add(player);
		this.addKeyListener(this);
		
		
		timer = new Timer(30, new TimerListener());
		timer.start();
	}
	
	
	public void paintComponent(Graphics g) {
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		super.paintComponent(g);
		
		g.drawImage(backgroundImg.getImage(), 0, 0, screenWidth, screenHeight, null);
		// draw actors
		for (Actor obj : actors) {
			obj.draw(g);
		}
		for (LevelObject ob : objects) {
			ob.draw(g);
		}
		
	}



	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	public void keyPressed(KeyEvent e) {
		   int keyCode = e.getKeyCode();
		    switch( keyCode ) { 
		        case KeyEvent.VK_UP:
		        case KeyEvent.VK_W:
		        case KeyEvent.VK_SPACE:
		            // handle jump 
		        	player.getVelocity().setDY((player.getAcceleration().getDY())-10);
		            break;
		        //  Move Left
		        case KeyEvent.VK_LEFT:
		        case KeyEvent.VK_A:
		        	player.velocity.setDX((player.getAcceleration().getDX())-3);
		            break;
		        // Move Right
		        case KeyEvent.VK_RIGHT :
		        case KeyEvent.VK_D:
		        	player.velocity.setDX((player.getAcceleration().getDX())+3);
		            break;
		     }
		    repaint();
		
	}

	
	public void keyReleased(KeyEvent e) {
	while (player.getAcceleration().getDX() != 0){
			if(player.getAcceleration().getDX() > 0){
				player.acceleration.setDX((player.getAcceleration().getDX())-1);
			}
			else if(player.getAcceleration().getDX() < 0){
				player.acceleration.setDX((player.getAcceleration().getDX())+1);
			}
		}
	
	}
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// move each Actor
			for (Actor ob: actors) {
				Actor ob2 = (Actor) ob;
				ob2.move();
			}
			for (LevelObject obj: objects) {
				obj.move();
			}
			
			repaint();
			
		}
		
	}
	
	/**
	 * @return the timer
	 */
	public javax.swing.Timer getTimer() {
		return timer;
	}
	/**
	 * @param timer the timer to set
	 */
	public void setTimer(javax.swing.Timer timer) {
		this.timer = timer;
	}
}

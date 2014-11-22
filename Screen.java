import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * Create a Screen object to serve as the current level in the Murio game.
 * Instantiates the LevelObjects and serves as the main game loop.
 * Contains the ArrayLists that contain LevelObjects and actors.
 * Contains the KeyListeners for controlling Murio.
 * 
 * @author RyanPierce
 *
 */
public class Screen extends JPanel implements KeyListener, Runnable{
	// Make Eclipse stop yelling at me.
	private static final long serialVersionUID = -3859645292224232330L;
	// Set the file to be used for the level.
	public final String LEVEL_NAME = "levels/level1_1.txt";
	
	/*
	 *  Set the style layout for the level.
	 *  Can be 0, 2, 4, or 6.
	 *  0 = Basic Level.
	 *  2 = Dark Level.
	 *  4 = Dungeon Level.
	 *  6 = Underwater Level.
	 *  Odd numbers can produce weird things if you want.
	 */
	public final int LEVEL_STYLE = 0;
	// Set Screen width and height.
	public static Dimension screenSize = new Dimension(700, 500);
	// The background image to use for the level.
	public static ImageIcon backgroundImg = new ImageIcon("img/happy_background.jpg");
	// The level timer.
	protected Timer timer;
	/* Set up the images from the specified sprite sheet.
	 * Specify:
	 * 		Number of rows of Images in sprite sheet.
	 * 		Number of columns of Images in the sprite sheet.
	 * 		Width in pixels of each Image on sprite sheet.
	 * 		Height of each Image on sprite sheet.
	 * 		The file name of the sprite sheet.
	 */
	protected ImageArray tileImages = new ImageArray(20, 32, 16, 16, "tileSets/tiles.png");
	// Initialize Player for Player 1.
	protected Player player;
	// Initialize the current level.
	protected Level currentLevel;
	
	/**
	 * Create the Screen which will display as the current level.
	 */
	public Screen() {
		// Set Screen size based on Dimension object.
		setPreferredSize(screenSize);
		// Set default background color.
		setBackground(Color.blue);
		//  Allows KeyboardListener to be used in the level.
		setFocusable(true);
		requestFocusInWindow();		
		
		// Add a KeyListener for keyboard input.
		this.addKeyListener(this);
		
		//Initialize Level
		currentLevel = new Level(0, 0, LEVEL_STYLE, LEVEL_NAME, tileImages);
		player = currentLevel.player;

		// Add a Timer for the Level
		timer = new Timer(30, new TimerListener());
		timer.start();
	}
	
	// Speed of background image scroll.
	public int speed = 0;
	
	/**
	 * Used for painting movements on Screen.
	 * 
	 * @param g The Graphics object for drawing.
	 */
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//  Get width and height of screen.
		screenSize.width= this.getWidth();
		screenSize.height = this.getHeight();
		
		if (player.location.getX() > screenSize.width/2){
			shiftLeft(g);
		}

		// Draw and tile background image.
		Image image = backgroundImg.getImage();
		if (true) {
            int iw = image.getWidth(this);
            int ih = image.getHeight(this);
            if (iw > 0 && ih > 0) {
                for (int x = speed; x < getWidth(); x += iw) {
                    for (int y = 0; y < getHeight(); y += ih) {
                        g.drawImage(image, x, y, iw, screenSize.height, null);
                    }
                }
            }
		}
		
		// draw actors
		for (Actor obj : currentLevel.getActors()) {
			obj.draw(g);
		}
		// draw LevelObjects.
		
		
		currentLevel.updateOnScreenObjects();
		for (LevelObject ob : currentLevel.getLevelObjects()) {
			ob.setLocation(new Point((int)(ob.getOriginalLocation().getX() + currentLevel.getGlobalOffset()), (int)(ob.getOriginalLocation().getY())));
			ob.draw(g);
		}
	}
	
	public synchronized void shiftLeft(Graphics g){
		player.setLocation(new Point((int)player.getLocation().getX()-2, (int)player.getLocation().getY()));
		currentLevel.setGlobalOffset(currentLevel.getGlobalOffset()-2);
		//for (LevelObject ob : currentLevel.getLevelObjects()) {
			//ob.setLocation((new Point(ob.getOriginalLocation().x + currentLevel.GLOBAL_OFFSET, ob.getOriginalLocation().y)));
		//}
	    speed -= 1;
	    repaint();
	}

	public synchronized void keyPressed(KeyEvent e) {
		   int keyCode = e.getKeyCode();
		    switch( keyCode ) { 
		        case KeyEvent.VK_UP:
		        case KeyEvent.VK_W:
		        case KeyEvent.VK_SPACE:
		            // handle jump 
		        	player.getVelocity().setDY((player.getAcceleration().getDY())-12);
		            break;
		        //  Move Left
		        case KeyEvent.VK_LEFT:
		        case KeyEvent.VK_A:
		        	player.setVelocity(new Vector(player.getAcceleration().getDX()-6, player.getAcceleration().getDirection()));
		            break;
		        // Move Right
		        case KeyEvent.VK_RIGHT :
		        case KeyEvent.VK_D:
		        	player.setVelocity(new Vector(player.getAcceleration().getDX()+6, player.getAcceleration().getDirection()));
		            break;
		     }		
	}

	
	public synchronized void keyReleased(KeyEvent e) {	
	}
	private class TimerListener implements ActionListener {
		public synchronized void actionPerformed(ActionEvent arg0) {
			// Check for collisions.
	        new Thread(new Runnable() {
	            public void run() {
	            	try{
	            		Image image = backgroundImg.getImage();
						for (LevelObject currentObject: currentLevel.getLevelObjects()) {
							// Player is to the Right of the current object.
							if(player.collide(currentObject).equals("LEFT_COLLISION")){
								//System.out.println("LEFT COLLISION");
								currentObject.setImage(image);
								player.location.x +=5;
								repaint();
							}
							// Player is to the Left of object.
							if(player.collide(currentObject).equals("RIGHT_COLLISION")){
								//System.out.println("RIGHT COLLISION");
								player.location.x -=5;
								repaint();
							}
							// Player is Below object.
							if(player.collide(currentObject).equals("TOP_COLLISION")){
								//System.out.println("TOP COLLISION");
								currentObject.setImage(image);
								player.acceleration.setMagnitude(0);
								player.velocity.setMagnitude(0);
								player.location.y +=5;
								repaint();
							}
							// Player is on top of object.
							if(player.collide(currentObject).equals("BOTTOM_COLLISION")){
								//System.out.println("BOTTOM COLLISION");
								currentObject.setImage(image);
								player.acceleration.setMagnitude(0);
								player.velocity.setMagnitude(0);
								player.location.y -=5;
								repaint();
							}
							
							else{
								player.acceleration.setDY(player.GRAVITY);
								repaint();
							}
						}
	            	}
	            	catch (ConcurrentModificationException e){
	            		//
	            	}
	            }
	        }).start();
			
			// move each Actor
			for (Actor ob: currentLevel.getActors()) {
				Actor ob2 = (Actor) ob;
				ob2.move();
			}			
		}
	}
	
	public void keyTyped(KeyEvent e) {
		// not used.	
	}
	
	/**
	 * @return the timer
	 */
	public synchronized javax.swing.Timer getTimer() {
		return timer;
	}
	/**
	 * @param timer the timer to set
	 */
	public synchronized void setTimer(javax.swing.Timer timer) {
		this.timer = timer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

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
	


	private static final long serialVersionUID = -3859645292224232330L;

	/*
	 * Set the file to be used for the level.
	 */
	public final String LEVEL_NAME = "levels/level1_2.txt";
	
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
	protected ImageArray tileImages = new ImageArray(20, 32, 16, 16, "tileSets/tiles.png");

	
	protected Level currentLevel;
	
	/**
	 * Create the Screen which will serve as the current level.
	 */
	public Screen() {
		// Set Screen size based on Dimension object.
		setPreferredSize(screenSize);
		setBackground(Color.blue);
		//  Allows KeyboardListener to be used in the level.
		setFocusable(true);
		requestFocusInWindow();
		
		
		
		// Add a KeyListener for keyboard input.
		Thread t1 = new Thread();
		t1.start();
		this.addKeyListener(this);
		
		//Initialize Level
		currentLevel = new Level(0, 0, LEVEL_STYLE, LEVEL_NAME, tileImages);

		// Add a Timer for the Level
		timer = new Timer(30, new TimerListener());
		timer.start();
	}
	public int speed = 0;
	Image image;
	
	public synchronized void paintComponent(Graphics g) {


		screenSize.width= this.getWidth();
		screenSize.height = this.getHeight();
		super.paintComponent(g);
		
		int xOffset = (int)currentLevel.player.location.getX();
		
		if (xOffset > screenSize.width/2){
			shiftLeft(g);
		}

		// Draw and tile background image.
		image = backgroundImg.getImage();
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
			if (ob.getLocation().x + currentLevel.GLOBAL_OFFSET.x < 700){
				ob.draw(g);	
			}
		}
		
		/*
		 * Test print level tiles for testing where collisions SHOULD be.
		 * 
		int count = 0;
		for (int y=0; y < currentLevel.getHeight(); y++){
			for (int x=0; x < currentLevel.getWidth(); x++){
				g.drawImage(currentLevel.getTiles().get(count), x*32, y*32, 32, 32, null);
				count++;
			}
		}
		*
		*
		*/
		//  This keeps scrolling and player movement in sync.
        new Thread(new Runnable() {
            public void run() {
            	repaint();
            }
        }).start();
	}
	
	public synchronized void shiftLeft(Graphics g){
		currentLevel.player.setLocation(new Point((int)currentLevel.player.getLocation().getX()-2, (int)currentLevel.player.getLocation().getY()));
		currentLevel.setGlobalOffset(new Point((currentLevel.getGlobalOffset().x-2), currentLevel.getGlobalOffset().y));
	    speed -= 1;
	}

	public synchronized void keyPressed(KeyEvent e) {
		   int keyCode = e.getKeyCode();
		    switch( keyCode ) { 
		        case KeyEvent.VK_UP:
		        case KeyEvent.VK_W:
		        case KeyEvent.VK_SPACE:
		            // handle jump 
		        	currentLevel.player.getVelocity().setDY((currentLevel.player.getAcceleration().getDY())-12);
		            break;
		        //  Move Left
		        case KeyEvent.VK_LEFT:
		        case KeyEvent.VK_A:
		        	//currentLevel.player.setVelocity(new Vector(0, currentLevel.player.getAcceleration().getDirection()));
		        	currentLevel.player.setVelocity(new Vector(0, currentLevel.player.getVelocity().getDirection()));
		        	currentLevel.player.setVelocity(new Vector(currentLevel.player.getAcceleration().getDX()-8, currentLevel.player.getAcceleration().getDirection()));
		            break;
		        // Move Right
		        case KeyEvent.VK_RIGHT :
		        case KeyEvent.VK_D:
		        	currentLevel.player.setVelocity(new Vector(0, currentLevel.player.getVelocity().getDirection()));
		        	currentLevel.player.setVelocity(new Vector(currentLevel.player.getAcceleration().getDX()+8, currentLevel.player.getAcceleration().getDirection()));
		            break;
		     }
		    repaint();
		
	}

	
	public synchronized void keyReleased(KeyEvent e) {	
	}
	
	private class TimerListener implements ActionListener {
		public synchronized void actionPerformed(ActionEvent arg0) {
			// Check for collisions.
	        new Thread(new Runnable() {
	            public void run() {
	            	try{
						for (LevelObject currentObject: currentLevel.getLevelObjects()) {
							// Player is to the Right of the current object.
							if(currentLevel.player.collide(currentObject).equals("LEFT_COLLISION")){
								currentLevel.player.location.x +=5;
							}
							// Player is to the Left of object.
							if(currentLevel.player.collide(currentObject).equals("RIGHT_COLLISION")){
								currentLevel.player.location.x -=5;
							}
							// Player is Below object.
							if(currentLevel.player.collide(currentObject).equals("TOP_COLLISION")){
								currentLevel.player.acceleration.setMagnitude(0);
								currentLevel.player.velocity.setMagnitude(0);
								currentLevel.player.location.y +=5;
							}
							// Player is on top of object.
							if(currentLevel.player.collide(currentObject).equals("BOTTOM_COLLISION")){
								currentLevel.player.acceleration.setMagnitude(0);
								currentLevel.player.velocity.setMagnitude(0);
								currentLevel.player.location.y -=5;
							}
							
							else{// (player.location.y != currentObject.location.y - player.size.height){
								currentLevel.player.acceleration.setDY(currentLevel.player.GRAVITY);
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

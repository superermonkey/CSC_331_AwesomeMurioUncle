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
	// Set the file to be used for the level.
	public final String LEVEL_NAME = "levels/level1_3.txt";
	
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
	protected ImageArray levelImages = new ImageArray(20, 32, 16, 16, "tileSets/tiles.png");
	// Image array for the itemImages, in the same format as levelImages
	protected ImageArray itemImages = new ImageArray(14, 36, 16, 16, "tileSets/items.png");
	// Image array for the enemyImages, in the same format as levelImages
	protected ImageArray enemyImages = new ImageArray(8, 50, 16, 16, "tileSets/enemies.png");
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
		currentLevel = new Level(0, 0, LEVEL_STYLE, LEVEL_NAME, levelImages, itemImages, enemyImages);
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
                    g.drawImage(image, x, 1, iw, screenSize.height, null);
                }
            }
		}
		g.setColor(Color.BLACK);
		g.drawString("Coins: " + player.getCoinCount(), 10, 20);
		g.drawString("Points: " + player.getPoints(), 70, 20);
		g.drawString("Lives: " + player.getNumberOfLives(), 140, 20);
		
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
	/**
	 * Shifts the level left when Murio reaches the halfway point across the Screen.
	 * 
	 * @param g The Graphics object.
	 */
	public synchronized void shiftLeft(Graphics g){
		currentLevel.setGlobalOffset(currentLevel.getGlobalOffset()-2);
		player.setLocation(new Point((int)player.getLocation().getX()-2, (int)player.getLocation().getY()));
		
		for (LevelObject ob : currentLevel.getLevelObjects()) {
			ob.setLocation((new Point(ob.getOriginalLocation().x + currentLevel.GLOBAL_OFFSET, ob.getOriginalLocation().y)));
		}
		// Set scroll speed of Background.
		// Lower number is slower.
		// Higher number is faster.
	    speed -= 1;
	    repaint();
	}

	/**
	 * Check key input from keyboard.
	 * Use keys to control Murio.
	 * 
	 * @param e The KeyEvent.
	 */
	public synchronized void keyPressed(KeyEvent e) {
		   int keyCode = e.getKeyCode();
		    switch(keyCode) { 
		        case KeyEvent.VK_UP:
		        case KeyEvent.VK_W:
		        case KeyEvent.VK_SPACE:
		            // handle jump 
		        	if(player.isCanJump()){
			        	player.acceleration.setDY(-12);
			        	player.setAcceleration(player.acceleration);
						player.setVelocity(player.velocity);
						player.setCanJump(false);
		        	}
		            break;
		        //  Move Left
		        case KeyEvent.VK_LEFT:
		        case KeyEvent.VK_A:
		        	player.velocity.setDX(-5);
		        	player.setAcceleration(player.acceleration);
					player.setVelocity(player.velocity);
		            break;
		        // Move Right
		        case KeyEvent.VK_RIGHT :
		        case KeyEvent.VK_D:
		        	player.velocity.setDX(5);
		        	player.setAcceleration(player.acceleration);
					player.setVelocity(player.velocity);
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
	            		// Check player's interaction with other actors (enemies, powerups)
	            		for (Actor currentActor: currentLevel.getActors()) {
	            			if(player.collide(currentActor).equals("GOOMBA_TOP")){
	            				currentActor.setSize(new Dimension(0,0));
	            				currentLevel.allLevelObjects.remove(currentActor);
								currentLevel.actors.remove(currentActor);
	            			}
	            			else if(player.collide(currentActor).equals("GOOMBA_KILL")){
	            				player.loseLife();
	            			}
	            		}
	            		for (Actor actor: currentLevel.getActors()){
							for (LevelObject currentObject: currentLevel.getLevelObjects()) {
								// Collide with Coin.
								if(actor.collide(currentObject).equals("COIN")){
									if (actor instanceof Player){
										Player actor2 = (Player)actor;	
										actor2.addCoin();
										currentLevel.allLevelObjects.remove(currentObject);
										currentLevel.levelObjects.remove(currentObject);
									}
									repaint();
								}
								// Collide with Brick.
								else if(actor.collide(currentObject).equals("BRICK")){
									Brick brick = (Brick)currentObject;
									if(brick.isBreakable && actor instanceof Player){
										Player actor2 = (Player) actor;
										actor2.addPoints(10);
										currentLevel.allLevelObjects.remove(currentObject);
										currentLevel.levelObjects.remove(currentObject);
										actor2.acceleration.setDY(0);
										actor2.velocity.setDY(0);
										actor2.acceleration.setDX(0);
										actor2.velocity.setDX(0);
										actor2.location.y -=5;
										actor2.setAcceleration(actor2.acceleration);
										actor2.setVelocity(actor2.velocity);
										actor2.setCanJump(true);
									repaint();
									}
									else{
										actor.acceleration.setDY(0);
										actor.velocity.setDY(0);
										actor.location.y -=5;
										actor.setAcceleration(actor.acceleration);
										actor.setVelocity(actor.velocity);
										repaint();
									}
								}
								// Player is on top of object.
								else if(actor.collide(currentObject).equals("BOTTOM_COLLISION")){
									actor.acceleration.setDY(0);
									actor.velocity.setDY(0);
									actor.location.setLocation(actor.location.x, currentObject.location.y - actor.size.getHeight());
									actor.setAcceleration(actor.acceleration);
									actor.setVelocity(actor.velocity);
									actor.setCanJump(true);
									repaint();
								}
								// Player is to the Right of the current object.
								else if(actor.collide(currentObject).equals("LEFT_COLLISION")){
									actor.velocity.setDX(0);
									actor.acceleration.setDX(0);
									actor.location.setLocation(currentObject.location.x + currentObject.getSize().getWidth(), actor.location.y);
									actor.setAcceleration(actor.acceleration);
									actor.setVelocity(actor.velocity);
									repaint();
								}
								// Player is to the Left of object.
								else if(actor.collide(currentObject).equals("RIGHT_COLLISION")){
									actor.velocity.setDX(0);
									actor.acceleration.setDX(0);
									actor.location.setLocation(currentObject.location.x - actor.getSize().getWidth(), actor.location.y);
									actor.setAcceleration(actor.acceleration);
									actor.setVelocity(actor.velocity);
									repaint();
								}
								// Player is Below object.
								else if(actor.collide(currentObject).equals("TOP_COLLISION")){
									actor.acceleration.setDY(0);
									actor.velocity.setDY(0);
									actor.location.y +=10;
									actor.setAcceleration(actor.acceleration);
									actor.setVelocity(actor.velocity);
									actor.setCanJump(true);
									repaint();
								}
								else{
									actor.acceleration.setDY(actor.GRAVITY);
									repaint();
								}
							}
		            	}
	            	}
	            	catch (ConcurrentModificationException e){
	            	}
	            }
	        }).start();
			
			//move each Actor
			for (Actor ob: currentLevel.getActors()) {
				ob.move();
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

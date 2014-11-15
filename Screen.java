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
 * Create a Screen object to serve as the current level in the Murio game.
 * Instantiates the LevelObjects and serves as the main game loop.
 * Contains the ArrayLists that contain LevelObjects and actors.
 * Contains the KeyListeners for controlling Murio.
 * 
 * @author RyanPierce
 *
 */
public class Screen extends JPanel implements KeyListener{
	// Set Screen width and height.
	public static Dimension screenSize = new Dimension(700, 500);
	// The background image to use for the level.
	public static ImageIcon backgroundImg = new ImageIcon("img/happy_background.jpg");
	//  The current image for the main playable character.
	public static ImageIcon playerImg = new ImageIcon("img/Mario_walk.gif");
	// ArrayList containing all of the moving Actor objects for the level.
	protected ArrayList<Actor> actors;
	// ArrayList containing all of the non-moving Tile objects for the game.
	protected ArrayList<LevelObject> objects;
	// The level timer.
	protected Timer timer;
	// The player object for first player.
	protected Player player;
	//  The Ground object.
	protected Ground ground;
	
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
		
		//Array to hold all the actors in the level.
		actors = new ArrayList<Actor>();
		// Array to hold all of the objects in the level, including stationary ones like ground and boxes.
		objects = new ArrayList<LevelObject>();
		
		// Create Player.
		Point playerLocation = new Point(50,200);
		Dimension playerSize = new Dimension(30,50);
		boolean playerVisibility = true;
		Vector playerVelocity = new Vector(0,0);
		player = new Player(playerLocation, playerSize, playerVisibility, playerVelocity, playerImg.getImage());
		
		
		// Create Ground
		Point groundLocation = new Point(0, 450);
		Dimension groundSize = new Dimension(500, GroundSection.BLOCK_SIZE.height);
		boolean groundVisibility = true;
		ground = new Ground(groundLocation, groundSize, groundVisibility, playerImg.getImage());
		ground.buildGround((int)groundSize.width/25);
		
		// Add static objects (ground, bricks, boxes) to objects ArrayList.
		// Add moving objects (players, enemies, shrooms) to actors ArrayList.
		objects.add(ground);
		actors.add(player);
		
		// Add a KeyListener for keyboard input.
		this.addKeyListener(this);
		
		//Initialize Level
		currentLevel = new Level(200, 50, 0, "levels/level1_1.txt", tileImages);
		
		
		// Add a Timer for the Level
		timer = new Timer(30, new TimerListener());
		timer.start();
	}
	
	
	public void paintComponent(Graphics g) {
		screenSize.width= this.getWidth();
		screenSize.height = this.getHeight();
		super.paintComponent(g);
		
		g.drawImage(backgroundImg.getImage(), 0, 0, screenSize.width, screenSize.height, null);
		/*
		for (int i = 0; i < tileImages.getImages().size(); i++){
			g.drawImage(tileImages.getImages().get(i), i*32, 0, 32, 32, null);
		}
		*/
		for (int i = 0; i < currentLevel.getWidth(); i++){
			for (int j = 0; j < currentLevel.getHeight(); j++){
				g.drawImage(currentLevel.getTile(i, j), i*32, j*32, 32, 32, null);
			}
		}
		
		System.out.println(currentLevel.getWidth() + "  " + currentLevel.getHeight());
		System.out.println(currentLevel.getTiles().size());
		g.drawImage(currentLevel.getTile(211, 15), 0, 0, 32, 32, null);
		// draw actors
		for (Actor obj : actors) {
			obj.draw(g);
		}
		// draw LevelObjects.
		for (LevelObject ob : objects) {
			ob.draw(g);
		}
		
	}

	
	public void buildLevel(int levelType){
		switch(levelType){
		//Basic Level Type
		case 1: {
			
			break;
		}
		// Dark Level type
		case 2: {
			break;
		}
		//Snow Level Type
		case 3:{
			break;
		}
		// Green Level Type
		case 4:
			break;
		default:
			break;
		
		}
	}


	public void keyTyped(KeyEvent e) {
		// not used.
		
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
			// Check for collisions.
			for (LevelObject currentObject: objects) {
				if (player.collide(currentObject)){
					player.acceleration.setDY(0);
					player.velocity.setDY(0);
					player.location.y = currentObject.location.y - player.size.height;
				}
				if (player.location.y != currentObject.location.y - player.size.height){
					player.acceleration.setDY(player.GRAVITY);
				}
			}
			
			
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

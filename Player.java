import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

/**
 * The player to be controller by the game's user.
 * Contains coinCount, points, and number of lives.
 * @author RyanPierce
 *
 */
public class Player extends Actor{
	// The maximum speed of the Player.
	public static final double MAX_SPEED = 10;
	// The number of coins the player has.
	private int coinCount = 0;
	// The number of points the player has.
	private int points = 0;
	// The number of Lives the Player has.
	private int numberOfLives = 3;

	
	/**
	 * Create a new Player for the Level. This will be the character that the game user will control.
	 * 
	 * @param l The initial location of the Player, in Point(x, y) form.
	 * @param d The size of the Player, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Brick.
	 * @param velocity The initial speed and direction of the Player.
	 * @param i The Image to display for the Player.
	 */
	public Player(Point l, Dimension d, boolean v, Vector velocity, Image i) {
		super(l, d, v, velocity, i);
	}
	
	/**
	 *  Dictates the movement of the Player.
	 *  	Player cannot go past the left side of the Screen.
	 *  	If Player falls below bottom of the Screen (ie, a pit) Player loses a life and respawns at (0,0) of the current Screen.
	 */
	public void move(){
		if (velocity.getDX() < MAX_SPEED){
			velocity.setDX(velocity.getDX()+acceleration.getDX());
		}
		if (velocity.getDY() < MAX_SPEED){
			velocity.setDY(velocity.getDY()+acceleration.getDY());
		}
		
		location.x += velocity.getDX();
		location.y += velocity.getDY();
		
		if (location.x > Screen.screenSize.getWidth()-this.size.getWidth()) {
			location.x = (int)(Screen.screenSize.getWidth()-this.size.getWidth());
			this.velocity.setDX(0);
		}
		if (location.x < 0) {
			location.x = 1;
		}
		
		if (location.y > Screen.screenSize.getHeight()) {
			this.loseLife();
			
			location.y = 0;
			location.x = 0;
		}
		if (location.y < 0) {
			location.y = 1;
		}
		
	}
	/**
	 * @param g The Graphics object.  Draws the Player at the current location.
	 */
	public void draw(Graphics g) {
		super.draw(g);
		g.drawImage(this.image, location.x, location.y, size.width, size.height, null);
	}
	/**
	 * @return The number of coins the player has
	 */
	public int getCoinCount() {
		return coinCount;
	}
	/**
	 * @param coinCount the coinCount to set
	 */
	public void addCoin() {
		this.coinCount += 1;
		if (this.coinCount >= 100){
			this.addLives(1);
			this.coinCount -=100;
		}
	}
	
	public int getPoints(){
		return this.points;
	}
	public void addPoints(int pointValue) {
		this.points += pointValue;
	}
	
	public int getNumberOfLives(){
		return this.numberOfLives;
	}
	public void addLives(int livesToAdd) {
		this.numberOfLives += livesToAdd;
	}
	public void loseLife(){
		this.numberOfLives -= 1;
		this.location.x = 0;
		this.location.y = 0;
	}
}
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

/**
 * @author RyanPierce
 *
 */
public class Player extends Actor{
	public static final double MAX_SPEED = 10;
	private int coinCount = 0;
	private int points = 0;
	/**
	 * @param l
	 * @param d
	 * @param v
	 * @param vector
	 */
	public Player(Point l, Dimension d, boolean v, Vector velocity, Image i) {
		super(l, d, v, velocity, i);
	}
	
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
			location.y = (int)Screen.screenSize.getHeight()-1;
		}
		if (location.y < 0) {
			location.y = 1;
		}
		
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.drawImage(this.image, location.x, location.y, size.width, size.height, null);
	}
	/**
	 * @return the coinCount
	 */
	public int getCoinCount() {
		return coinCount;
	}
	/**
	 * @param coinCount the coinCount to set
	 */
	public void addCoin() {
		this.coinCount += 1;
	}
	
	public int getPoints(){
		return this.points;
	}
	public void addPoints(int pointValue) {
		this.points += pointValue;
	}
}
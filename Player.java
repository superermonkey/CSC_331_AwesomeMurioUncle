import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;


/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Player extends Actor{
	public static final double MAX_SPEED = 10;
	
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
		
		if (location.x > Screen.screenWidth) {
			location.x -= Screen.screenWidth;
		}
		if (location.x < 0) {
			location.x += Screen.screenWidth;
		}
		
		if (location.y > Screen.screenHeight) {
			location.y -= Screen.screenHeight;
		}
		if (location.y < 0) {
			location.y += Screen.screenHeight;
		}
	}

	/* (non-Javadoc)
	 * @see LevelObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.image, location.x, location.y, size.width, size.height, null);
	}
}
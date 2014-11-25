import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * Class for all of the Actors not controlled by Murio.
 * Enemies have their own standard movements and can be killed.
 * 
 * @author RyanPierce
 *
 */
public abstract class Enemy extends Actor{
	
	// The number of points Murio gets when killing this Enemy.
	protected int pointValue;
	
	/**
	 * Create a new Enemy for the Level.
	 * 
	 * @param l The initial location of the Enemy, in Point(x, y) form.
	 * @param d The size of the Enemy, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Brick.
	 * @param vector The initial speed and direction of the Enemy.
	 * @param i The Image to display for the Enemy.
	 * @param point The point value of the Enemy.
	 */
	public Enemy(Point l, Dimension d, boolean v, Vector vector, Image i, int point) {
		super(l, d, v, vector, i);
		this.pointValue = point;
	}

	/**
	 * @return the pointValue of the Enemy.
	 */
	public int getPointValue() {
		return pointValue;
	}

	/**
	 * @param g The Graphics object.
	 */
	public void draw(Graphics g) {	
		super.draw(g);
	}

	/**
	 * The movement of this Enemy.
	 */
	public abstract void move();
}

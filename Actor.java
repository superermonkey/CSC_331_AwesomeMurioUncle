import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import com.sun.prism.Graphics;

/**
 * 
 */

/**
 * @author Monkey
 *
 */
public abstract class Actor extends LevelObject{

	protected Vector velocity = new Vector(0, 0);
	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public Actor(Point l, Dimension d, boolean v, Vector vector, Image i) {
		super(l, d, v, i);
		this.velocity = vector;
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g) {
		
	}

}

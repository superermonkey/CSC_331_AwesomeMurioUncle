import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;

import com.sun.prism.Graphics;

/**
 * 
 */

/**
 * @author Monkey
 *
 */
public abstract class Actor extends LevelObject{

	protected Vector velocity = new Vector();
	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public Actor(Point l, Dimension d, boolean v, Vector vector) {
		super(l, d, v);
		this.velocity = vector;
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}

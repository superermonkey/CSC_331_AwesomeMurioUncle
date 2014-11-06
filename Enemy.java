import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;


/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Enemy extends Actor{

	protected int pointValue;
	/**
	 * @param l
	 * @param d
	 * @param v
	 * @param vector
	 */
	public Enemy(Point l, Dimension d, boolean v, Vector vector, int point) {
		super(l, d, v, vector);
		this.pointValue = point;
	}

	/**
	 * @return the pointValue
	 */
	public int getPointValue() {
		return pointValue;
	}

	/**
	 * @param pointValue the pointValue to set
	 */
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	/* (non-Javadoc)
	 * @see LevelObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}


}

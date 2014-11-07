import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;


/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Ground extends LevelObject{

	protected ImageIcon groundImage = new ImageIcon("ground_block.png");
	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public Ground(Point l, Dimension d, boolean v) {
		super(l, d, v, null);
	}
	/* (non-Javadoc)
	 * @see LevelObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see LevelObject#move()
	 */
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
}

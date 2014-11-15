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
public abstract class Box extends LevelObject{

	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public Box(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
		// TODO Auto-generated constructor stub
	}
	
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.image = this.getImage();
		g2.drawImage(this.image, this.location.x, this.location.y, this.size.height, this.size.width, null);
	}

}

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class GroundSection extends Ground{
	public static final Dimension blockSize = new Dimension(25,25);
	public static final ImageIcon blockImage = new ImageIcon("img/ground_block.png");
	
	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public GroundSection(Point l, boolean v) {
		super(l, blockSize, v, null);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.image = blockImage.getImage();
		g2.drawImage(this.image, this.location.x, this.location.y, (int)blockSize.getHeight(), (int)blockSize.getWidth(), null);
		}

}

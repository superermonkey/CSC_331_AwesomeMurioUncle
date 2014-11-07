import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;


/**
 * This class builds individual bricks for ground.
 * @author RyanPierce
 *
 */
public class GroundSection extends Ground{
	//  Set size, in pixels, of blocks. CAN BE SCALED to make Murio seem smaller or larger.
	public static final Dimension BLOCK_SIZE = new Dimension(25, 25);
	// Set image to be used for GroundSection.
	// Update later to allow different kinds of ground (enum).
	private ImageIcon blockImage = new ImageIcon("img/ground_block.png");
	
	/**
	 * @param l The x,y location of the top-left corner of the beginning of the GroundSection.
	 * @param v boolean true if GroundSection instance is visible.
	 */
	public GroundSection(Point l, boolean v) {
		// Construct GroundSection at given Point l, with size determined by BLOCK_SIZE final, with given visibility and NULL default Image.
		super(l, BLOCK_SIZE, v, null);
		this.isSolidAndStationary = true;
	}
	
	/**
	 * Draw the GroundSection object.
	 */
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.image = blockImage.getImage();
		g2.drawImage(this.image, this.location.x, this.location.y, (int)BLOCK_SIZE.getHeight(), (int)BLOCK_SIZE.getWidth(), null);
		}

}

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This class builds a ground for Murio to run on.
 * It works by adding a number of GroundSection objects to an array
 * each GroundSection in the array is then drawn a set distance away from the rest.
 * 
 * @author Ryan Pierce
 *
 */
public class Ground extends LevelObject{
	
	// ArrayList the stores all of the GroundSection objects that will make up this instance of Ground.
	private ArrayList<GroundSection> groundBlocks = new ArrayList<GroundSection>();
	
	/**
	 * @param l The x,y location of the top-left corner of the beginning of the Ground instance.
	 * @param d The width and height of this Ground instance.
	 * @param v boolean true if Ground instance is visible.
	 * @param i Image to be used for Ground instance.
	 */
	public Ground(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
		this.isSolidAndStationary = true;
	}
	
	/**
	 * Builds a Ground object. Made of the number of GroundSection objects it would take to fill the given width of the Ground object.
	 * @param size The horizontal size of the Ground instance, in number of GroundSection objects.  Determined by the width dimension of this Ground instance/width of a GroundSection.
	 */
	public void buildGround(int size){
		double multiplier = GroundSection.BLOCK_SIZE.getWidth();
		// Add GroundSection objects to the groundBlocks ArrayList.
		for(int j=1; j < size+1; j++){
				groundBlocks.add(new GroundSection((new Point((int)(this.location.getX()+(GroundSection.BLOCK_SIZE.getWidth()*j)), (int)this.location.getY())), this.isVisible));
			}
		}
		

	/**
	 * Draw the Ground instance by drawing each GroundSection object.
	 */
	public void draw(Graphics g) {
		// Iterate over groundBlocks array and draw each GroundSection object.
		for(int i=0; i < groundBlocks.size(); i++){
			groundBlocks.get(i).draw(g);
		}
		
	}
	/**
	 * Move the entire Ground instance.
	 */
	public void move() {
		// To be implemented.
	}
}

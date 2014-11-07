import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;


/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class Ground extends LevelObject{
	
	private ArrayList<GroundSection> groundBlocks = new ArrayList<GroundSection>();
	/**
	 * @param l
	 * @param d
	 * @param v
	 */
	public Ground(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
	}
	
	public void buildGround(int size){
		for(int j=1; j < size+1; j++){
				groundBlocks.add(new GroundSection((new Point((int)(this.location.getX()+25*j), (int)this.location.getY())), this.isVisible));
			}
		}
		

	public void draw(Graphics g) {
		for(int i=0; i < groundBlocks.size(); i++){
			System.out.println(i);
			groundBlocks.get(i).draw(g);
		}
		
	}
	public void move() {
	}
}

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * A Coin that can be picked up by Murio, adding to his coinCount.
 * 
 * @author RyanPierce
 *
 */
public class Coin extends LevelObject{

	/**
	 * Create a new Coin for the Level.
	 * 
	 * @param l The initial location of the Coin, in Point(x, y) form.
	 * @param d The size of the Coin, in Dimension(w, h) form.
	 * @param v The visibility boolean of the Coin.
	 * @param i The Image to display for the Coin.
	 */
	public Coin(Point l, Dimension d, boolean v, Image i) {
		super(l, d, v, i);
		// TODO Auto-generated constructor stub
	}


	public void draw(Graphics g) {
		super.draw(g);
	}

	public void move(){
		// Not Used.
	}



}

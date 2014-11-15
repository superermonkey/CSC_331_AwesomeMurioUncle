import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author Monkey
 *
 */
public class ImageArray {
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	private int rows;
	private int cols;
	private int width;
	private int height;
	
	public ImageArray(){
		
	}
	
	public ImageArray(int rows, int columns, int w, int h, String filePath){
		try{
			BufferedImage bigImage = ImageIO.read(new File(filePath));
			
			this.width = w;
			this.height = h;
			this.rows = rows;
			this.cols = columns;
			
			for (int i = 0; i < rows; i++)
			{
			    for (int j = 0; j < cols; j++)
			    {
			        images.add(bigImage.getSubimage(
			            j * width,
			            i * height,
			            width,
			            height
			        ));
			    }
			}
		}
		catch(Exception e){
			System.out.println("Invalid File");
		}
	}
	
	public ArrayList<BufferedImage> build(int rows, int columns, int w, int h, String filePath){
		try{
			BufferedImage bigImage = ImageIO.read(new File(filePath));
			
			this.width = w;
			this.height = h;
			this.rows = rows;
			this.cols = columns;
	
			for (int i = 0; i < rows; i++)
			{
			    for (int j = 0; j < cols; j++)
			    {
			        images.add(bigImage.getSubimage(
			            j * width,
			            i * height,
			            width,
			            height
			        ));
			    }
			}
		}
		catch(Exception e){
			System.out.println("Invalid File");
		}
		return images;
	}

	/**
	 * @return the images
	 */
	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(ArrayList<BufferedImage> images) {
		this.images = images;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @param cols the cols to set
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	
}

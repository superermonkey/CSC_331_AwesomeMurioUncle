import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Create an array of BufferedImages from a given sprite sheet, or general larger image.
 * Images are extracted based on number of rows and columns expected from larger image,
 * followed by the size to pull from each cell, in pixels.
 * 
 * @author RyanPierce
 *
 */
public class ImageArray {
	
	/*
	 * The ArrayList that will hold each of the sub-images, in order of extraction.
	 * Cells are filled from left to right and wrap to the next column based on the
	 * specified width in the constructor.
	 */
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	/*
	 * The number of rows to extract from the source image.
	 */
	private int rows;
	
	/*
	 * The number of columns to extract from the source image.
	 */
	private int cols;
	
	/*
	 * The width, in pixels, of each sub-image to extract.
	 */
	private int width;
	
	/*
	 * The height, in pixels, of each sub-image to extract.
	 */
	private int height;
	
	/**
	 * The default constructor.
	 */
	public ImageArray(){	
	}
	
	/**
	 * Builds a new ImageArray based of the given parameters.
	 * 
	 * @param rows The number or rows to generate from the source image.
	 * @param columns The number of columns expected from the source image.
	 * @param w The width of each sub image to be extracted.
	 * @param h The height of each sub image to be extracted.
	 * @param filePath The relative path of the source image.
	 */
	public ImageArray(int rows, int columns, int w, int h, String filePath){
		
		/*
		 * Block to catch missing files.
		 */
		try
		{
			//  The source image.
			BufferedImage bigImage = ImageIO.read(new File(filePath));
			
			// The other parameters.
			this.width = w;
			this.height = h;
			this.rows = rows;
			this.cols = columns;
			
			/*
			 *  Iterate through the source image and obtain sub images.
			 *  Add each sub image to the ArrayList of BufferedImages.
			 */
			for (int currentRow = 0; currentRow < rows; currentRow++)
			{
			    for (int currentColumn = 0; currentColumn < cols; currentColumn++)
			    {
			        images.add(bigImage.getSubimage(
			        	currentColumn * this.width,
			            currentRow * this.height,
			            this.width,
			            this.height
			        ));
			    }
			}
		}
		/*
		 * Kick here if the specified file does not exist.
		 */
		catch(Exception e)
		{
			System.out.println("Invalid File. The source image " + filePath + " does not exist.");
		}
	}

	/**
	 * Returns the BufferedImage at the specified location(row, column) if it exists.
	 * If the requested image is out of bounds, the default BufferedImage at (0,0) is returned.
	 * 
	 * @param row The row of the requested BufferedImage.
	 * @param column The column of the specified BufferedImage.
	 * @return The BufferedImage at the specified row and column.
	 */
	public BufferedImage get(int row, int column){
		if (row*column <= this.getImages().size()){
			return images.get(row*this.getCols() + column);
		}
		else{
			return this.get(0, 0);
		}
	}
	
	/*
	 * Clear the ImageArray.
	 */
	public void clear(){
		this.images.clear();
	}

	//
	//
	//
	// MINDLESS GETTERS AND SETTERS AFTER THIS POINT...!!!
	//
	//
	//
	/**
	 * Returns the entire ArrayList of BufferedImages.
	 * @return The entire ArrayList of BufferedImages.
	 */
	public ArrayList<BufferedImage> getImages() 
	{
		return images;
	}

	/**
	 * @return The number of rows in the ImageArray.
	 */
	public int getRows() 
	{
		return rows;
	}

	/**
	 * Set the number of rows in the ImageArray.
	 * @param rows The new number of rows in the ImageArray.
	 */
	public void setRows(int r) 
	{
		this.rows = r;
	}

	/**
	 * @return The number of columns in the ImageArray.
	 */
	public int getCols() 
	{
		return cols;
	}

	/**
	 * Set The number of columns in the ImageArray.
	 * @param cols Set The number of columns in the ImageArray.
	 */
	public void setCols(int cols) 
	{
		this.cols = cols;
	}

	/**
	 * The width of the sub images to extract/that have been extracted.
	 * @return The width of the sub images to extract/that have been extracted.
	 */
	public int getWidth() 
	{
		return width;
	}

	/**Set The width of the sub images to extract.
	 * @param width Set The width of the sub images to extract.
	 */
	public void setWidth(int width) 
	{
		this.width = width;
	}

	/**
	 * The height of the sub images to extract/that have been extracted.
	 * @return The height of the sub images to extract/that have been extracted.
	 */
	public int getHeight() 
	{
		return height;
	}

	/**Set The height of the sub images to extract.
	 * @param height Set the height of the sub images to extract.
	 */
	public void setHeight(int height) 
	{
		this.height = height;
	}
}

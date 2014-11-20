import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Create a Level "map" to be used in the Screen when it builds the level.
 * Level contains all of the definitions for what each block should be.
 * Level also contains the setters for the global Box size, and the levelOffset.
 * 
 * Most importantly, Level reads in the source text files and generates the playable levels for the Screen.
 * Level reads in characters, and applies the specified BufferedImage and creates tiles based on
 * the preset conditions and definitions.
 * 
 * @author RyanPierce
 * @author DeanAntel
 *
 */
public class Level{
	
	/*
	 * The ArrayList tiles contains a set of the BufferedImages, arranged
	 * and laid out according to the characters from the level text file.
	 * All blank tiles are simply specified as null.
	 * 
	 * The tiles ArrayList simply contains the locations and images to be used
	 * by the Screen class when it builds the LevelObjects for the level, using
	 * this tiles ArrayList as a "map"
	 * 
	 * In other words, the tiles ArrayList does not store LevelObjects,
	 * it only stores non-interactable  BufferedImages.
	 */
	private ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
	
	/*
	 * The ImageArray that contains all the possible static tiles for use in the Level.
	 * 
	 * Tiles in titleImageDictionary are ordered by row and column that
	 * they existed in the original source image.
	 */
	private ImageArray tileImageDictionary = new ImageArray();
	
	/*
	 * The ArrayList that contains all the possible actor tiles for use in the Level.
	 * 
	 * This array holds the definitions for the player and enemy instances
	 * as well as the animation frames for each. 
	 */
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	
	/*
	 * The ArrayList that contains all the possible static tiles for use in the Level.
	 * 
	 * This array holds the definitions for the boxes and blocks
	 * as well as the animation frames for those that require them. 
	 */
	private ArrayList<LevelObject> allLevelObjects = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>();
	
	/*
	 * The basic variables to hold the images for each tile type for the Level.
	 * Can easily be switched around simply by manipulating the LevelType in the Screen class.
	 */
	private int levelType = 0;
	private BufferedImage GROUND;
	private BufferedImage BRICK;
	private BufferedImage METAL_BOX;
	private BufferedImage QUESTION_MARK_BOX;
	private BufferedImage TOP_LEFT_PIPE;
	private BufferedImage TOP_RIGHT_PIPE;
	private BufferedImage LEFT_PIPE;
	private BufferedImage RIGHT_PIPE;
	private BufferedImage BEVELED_BRICK;
	private BufferedImage TOP_POLE;
	private BufferedImage POLE;
	private BufferedImage CASTLE_TOP;
	private BufferedImage CASTLE_WINDOW_LEFT;
	private BufferedImage CASTLE_WINDOW_CENTER;
	private BufferedImage CASTLE_WINDOW_RIGHT;
	private BufferedImage CASTLE_INTERIOR_TOP;
	private BufferedImage CASTLE_DOOR_TOP;
	private BufferedImage CASTLE_DOOR;
	

	/*
	 * Contains the globalOffset, in Point(x,y) form, to be used for interactive purposes.
	 * As Murio moves across the Screen, it keeps track of the WORLD x and y offset, to compensate for
	 * vertical or horizontal scrolling and allow collision to appear as if it is occurring normally.
	 */
	public static Point GLOBAL_OFFSET = new Point(0,0);
	
	/*
	 * Used to overall width and height (in characters from the text file) of the whole Level.
	 */
	private int width;
	private int height;
	
	/*
	 * Set the width and height (in pixels) to use for each BufferedImage.
	 * Meant to be square.
	 * Default is 32.
	 * 
	 * Please note that null spaces (empty space for Murio to walk around and jump through) will
	 * be scaled according to this size.  Basically "empty" tiles are created, of whatever
	 * size is set by imageHeight and imageWidth.
	 */
	private int imageHeight = 32;
	private int imageWidth = 32;
	
	// The player object for first player.
	protected Player player;
	
	/**
	 * The default constructor.
	 */
	public Level(){
	}
	
	/**
	 * Create a Level "map" to be used in the Screen when it builds the level.
	 * Level contains all of the definitions for what each block should be.
	 * Level also contains the setters for the global Box size, and the levelOffset.
	 * 
	 * Most importantly, Level reads in the source text files and generates the playable levels for the Screen.
	 * Level reads in characters, and applies the specified BufferedImage and creates tiles based on
	 * the preset conditions and definitions.
	 * 
	 * @param w The overall width of the Level, in characters.  Can be set manually or overridden by the map file if it is larger.
	 * @param h The overall height of the Level, in characters.  Can be set manually or overridden by the map file if it is larger.
	 * @param lvlType The type of Level(0=Basic, 2=Dark, 4=Dungeon, 6=Under water).
	 * @param mapFileName  The filename for the "map" text file.
	 * @param tileImageDictionary The ImageArray file to use for tile image definitions.
	 */
	public Level(int w, int h, int lvlType, String mapFileName, ImageArray tileImageDictionary){
		this.width = w;
		this.height = h;
		this.levelType = lvlType;
		this.tileImageDictionary = tileImageDictionary;
		
		/*
		 * Set up the dictionary of images to be used for the tiles.
		 * Sets all of the constant variables (ie BRICK, TOP_LEFT_PIPE) to match their
		 * corresponding images.
		 */
		this.setLevelConstants(lvlType);
		
		/*
		 * Read in the "map" of characters and set all the tiles to their images.
		 * If an unspecified character is encountered, it defaults to null.
		 */
		this.readMap(mapFileName);
		
		// Create Player.
		//  The current image for the main playable character.
		final ImageIcon playerImg = new ImageIcon("img/Mario_walk.gif");
		Point playerLocation = new Point(50,200);
		Dimension playerSize = new Dimension(35,55);
		boolean playerVisibility = true;
		Vector playerVelocity = new Vector(0,0);
		player = new Player(playerLocation, playerSize, playerVisibility, playerVelocity, playerImg.getImage());
		actors.add(player);
				
	}
	
	/*
	 * Set up the dictionary of images to be used for the tiles.
	 * Sets all of the constant variables (ie BRICK, TOP_LEFT_PIPE) to match their
	 * corresponding images.
	 * 
	 * The command "get" is in get(row, column) form.
	 */
	private void setLevelConstants(int levelType){
		this.GROUND = tileImageDictionary.get(0+levelType, 0);
		this.BRICK = tileImageDictionary.get(0+levelType, 13);
		this.METAL_BOX = tileImageDictionary.get(0+levelType, 3);
		this.QUESTION_MARK_BOX = tileImageDictionary.get(0+levelType, 24);
		this.TOP_LEFT_PIPE = tileImageDictionary.get(8+levelType, 0);
		this.TOP_RIGHT_PIPE = tileImageDictionary.get(8+levelType, 1);
		this.LEFT_PIPE = tileImageDictionary.get(9+levelType, 0);
		this.RIGHT_PIPE = tileImageDictionary.get(9+levelType, 1);
		this.BEVELED_BRICK = tileImageDictionary.get(1+levelType, 0);
		this.POLE = tileImageDictionary.get(9+levelType, 16);
		this.TOP_POLE = tileImageDictionary.get(8+levelType, 16);
		this.CASTLE_TOP = tileImageDictionary.get(0+levelType, 11);
		this.CASTLE_WINDOW_LEFT = tileImageDictionary.get(0+levelType, 12);
		this.CASTLE_WINDOW_CENTER = tileImageDictionary.get(0+levelType, 13);
		this.CASTLE_WINDOW_RIGHT = tileImageDictionary.get(0+levelType, 14);
		this.CASTLE_INTERIOR_TOP = tileImageDictionary.get(1+levelType, 11);
		this.CASTLE_DOOR_TOP = tileImageDictionary.get(1+levelType, 12);
		this.CASTLE_DOOR = tileImageDictionary.get(1+levelType, 13);
	}
	
	/**
	* Read in the "map" of characters and set all the tiles to their images.
	* If an unspecified character is encountered, it defaults to null.
	* 
	* @param fileName The filename for the "map" text file.
	*/
	private void readMap(String fileName){
		// ArrayList to hold the lines of the map file.
		ArrayList<String> lines = new ArrayList<String>();
		
		// Protect against missing text file.
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			// Level cannot be negative width.
			int widest = 0;
			
			// While there are still lines to be read.
			while (true) {
				// Read in the next line.
				String line = reader.readLine();
				// Exit loop if the line is empty.
				if (line == null){
					break;
				}
				
				/*
				 * File can contain a default levelType that overrides fixed input.
				 * 
				 * Syntax for file begins on a new line before the level-building characters start
				 * "~[levelType int]"
				 * So "~2" would specify levelType #2.
				 */
				if(line.startsWith("~")){
					this.setLevelType(Integer.parseInt(line.substring(1)));
				}
				// Add non-comment lines from level map file.
				if(!line.startsWith("#") && !line.startsWith("~")){     //ignore comments in level text file
					lines.add(line);
					if (line.length() > widest){ 
						widest = line.length();
					}
				}
			}
			reader.close();
			
			// Set new dimensions based on map file.
			this.setWidth(widest);
			this.setHeight(lines.size());
			
			/*
			 * Loop to go through all of the characters from the "map" file that are now stored in the lines ArrayList.
			 * LevelObjects are generated and added to the allLevelObjects array, and Actors to the actors array.
			 * 
			 *  The tiles array is also created to hold a visual "map" that can be toggled but not interacted with.
			 */
			for (int y = 0; y < height; y++)
			{
				String line = lines.get(y);
				for (int x = 0; x <line.length(); x++)
				{
					char type = line.charAt(x); 
					/*
					 * Nested loop that iterates over each character to determine which type of tile it should be.
					 * allLevelObjects are added at a Point(x,y) generated by the nested loops.
					 */
					if (type == 'G')
					{	

						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.GROUND));
						this.tiles.add(this.GROUND);
					}
					else if (type == 'B')
					{
						this.allLevelObjects.add(new Brick(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.BRICK));
						this.tiles.add(this.BRICK);
					}
					else if (type == 'Q')
					{
						this.allLevelObjects.add(new QuestionMarkBox(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.QUESTION_MARK_BOX));
						this.tiles.add(this.QUESTION_MARK_BOX);
					}
					else if (type == 'A')
					{
						this.allLevelObjects.add(new MetalBox(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.METAL_BOX));
						this.tiles.add(this.METAL_BOX);
					}
					else if (type == 'I')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.TOP_LEFT_PIPE));
						this.tiles.add(this.TOP_LEFT_PIPE);
					}
					else if (type == 'O')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.TOP_RIGHT_PIPE));
						this.tiles.add(this.TOP_RIGHT_PIPE);
					}
					else if (type == 'K')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.LEFT_PIPE));
						this.tiles.add(this.LEFT_PIPE);
					}
					else if (type == 'L')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.RIGHT_PIPE));
						this.tiles.add(this.RIGHT_PIPE);
					}
					else if (type == 'H')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.BEVELED_BRICK));
						this.tiles.add(this.BEVELED_BRICK);
					}
					else if (type == 'P')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.POLE));
						this.tiles.add(this.POLE);
					}
					else if (type == 'S')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.TOP_POLE));
						this.tiles.add(this.TOP_POLE);
					}
					else if (type == 'C')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_TOP));
						this.tiles.add(this.CASTLE_TOP);
					}
					else if (type == 'E')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_WINDOW_LEFT));
						this.tiles.add(this.CASTLE_WINDOW_LEFT);
					}
					else if (type == 'D')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_WINDOW_CENTER));
						this.tiles.add(this.CASTLE_WINDOW_CENTER);
					}
					else if (type == 'M')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_WINDOW_RIGHT));
						this.tiles.add(this.CASTLE_WINDOW_RIGHT);
					}
					else if (type == 'N')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_INTERIOR_TOP));
						this.tiles.add(this.CASTLE_INTERIOR_TOP);
					}
					else if (type == 'F')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_DOOR_TOP));
						this.tiles.add(this.CASTLE_DOOR_TOP);
					}
					else if (type == 'J')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_DOOR));
						this.tiles.add(this.CASTLE_DOOR);
					}
					/*
					 * If the given character cannot be found, simply create a null filler for the "map".
					 */
					else
					{
						this.tiles.add(null);
					}
				}
			}
		}
		// Catch the invalid input and warn.
		catch(IOException e)
		{
			System.out.println("Invalid Level Input File!! Level.readMap(String fileName) " + fileName);
		}
	}
	
	public void updateOnScreenObjects(){
		for (LevelObject ob : allLevelObjects) {
			if (ob.getLocation().x + GLOBAL_OFFSET.x < 700 && !levelObjects.contains(ob)){
				levelObjects.add(ob);
			}
			if (ob.getLocation().x + GLOBAL_OFFSET.x < 0- this.getImageWidth() && levelObjects.contains(ob)){
				levelObjects.remove(ob);
			}
		}
	}

	
	/**
	 * @return the levelOffset
	 */
	public void setGlobalOffset(Point gblOffset) {
		this.GLOBAL_OFFSET = gblOffset;
	}

	/**
	 * @return the levelType
	 */
	public int getLevelType() {
		return levelType;
	}

	/**
	 * @param levelType the levelType to set
	 */
	public void setLevelType(int levelType) {
		this.levelType = levelType;
		this.setLevelConstants(levelType);
	}
	/**
	 * @return the tiles
	 */
	public ArrayList<BufferedImage> getTiles() {
		return tiles;
	}
	
	
	public BufferedImage getTile(int x, int y){
		return tiles.get(y*this.getWidth() + (x));
	}
	
	public BufferedImage getTile(int i){
		return tiles.get(i);
	}


	/**
	 * @param tiles the tiles to set
	 */
	public void setTiles(ArrayList<BufferedImage> tiles) {
		this.tiles = tiles;
	}



	/**
	 * @return the actors
	 */
	public ArrayList<Actor> getActors() {
		return actors;
	}



	/**
	 * @param actors the actors to set
	 */
	public void setActors(ArrayList<Actor> actors) {
		this.actors = actors;
	}



	/**
	 * @return the levelObjects
	 */
	public ArrayList<LevelObject> getLevelObjects() {
		return levelObjects;
	}

	/**
	 * @param levelObjects the levelObjects to set
	 */
	public void setLevelObjects(ArrayList<LevelObject> levelObjects) {
		this.levelObjects = levelObjects;
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


	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}


	/**
	 * @param imageHeight the imageHeight to set
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}


	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}


	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the globalOffset
	 */
	public Point getGlobalOffset() {
		return GLOBAL_OFFSET;
	}
}

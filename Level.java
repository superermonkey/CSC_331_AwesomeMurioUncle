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
	private ArrayList<BufferedImage> levelTiles = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> itemTiles = new ArrayList<BufferedImage>();
	
	/*
	 * The ImageArray that contains all the possible static tiles for use in the Level.
	 * 
	 * Tiles in titleImageDictionary are ordered by row and column that
	 * they existed in the original source image.
	 */
	private ImageArray levelTileImageDictionary = new ImageArray();
	private ImageArray itemTileImageDictionary = new ImageArray();
	
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
	public ArrayList<LevelObject> allLevelObjects = new ArrayList<LevelObject>();
	public ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>();
	
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
	private BufferedImage COIN;

	/*
	 * Contains the globalOffset, in Point(x,y) form, to be used for interactive purposes.
	 * As Murio moves across the Screen, it keeps track of the WORLD x and y offset, to compensate for
	 * vertical or horizontal scrolling and allow collision to appear as if it is occurring normally.
	 */
	public static int GLOBAL_OFFSET = 0;
	
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
	 * @param levelTileImageDictionary The ImageArray file to use for tile image definitions.
	 */
	public Level(int w, int h, int lvlType, String mapFileName, ImageArray levelTileImageDictionary, ImageArray itemTileImageDictionary){
		this.width = w;
		this.height = h;
		this.levelType = lvlType;
		this.levelTileImageDictionary = levelTileImageDictionary;
		this.itemTileImageDictionary = itemTileImageDictionary;
		
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
		// The current image for the main playable character.
		final ImageIcon playerImg = new ImageIcon("img/Mario_walk.gif");
		Point playerLocation = new Point(50,200);
		Dimension playerSize = new Dimension(35,60);
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
		this.GROUND = levelTileImageDictionary.get(0+levelType, 0);
		this.BRICK = levelTileImageDictionary.get(0+levelType, 13);
		this.METAL_BOX = levelTileImageDictionary.get(0+levelType, 3);
		this.QUESTION_MARK_BOX = levelTileImageDictionary.get(0+levelType, 24);
		this.TOP_LEFT_PIPE = levelTileImageDictionary.get(8+levelType, 0);
		this.TOP_RIGHT_PIPE = levelTileImageDictionary.get(8+levelType, 1);
		this.LEFT_PIPE = levelTileImageDictionary.get(9+levelType, 0);
		this.RIGHT_PIPE = levelTileImageDictionary.get(9+levelType, 1);
		this.BEVELED_BRICK = levelTileImageDictionary.get(1+levelType, 0);
		this.POLE = levelTileImageDictionary.get(9+levelType, 16);
		this.TOP_POLE = levelTileImageDictionary.get(8+levelType, 16);
		this.CASTLE_TOP = levelTileImageDictionary.get(0+levelType, 11);
		this.CASTLE_WINDOW_LEFT = levelTileImageDictionary.get(0+levelType, 12);
		this.CASTLE_WINDOW_CENTER = levelTileImageDictionary.get(0+levelType, 13);
		this.CASTLE_WINDOW_RIGHT = levelTileImageDictionary.get(0+levelType, 14);
		this.CASTLE_INTERIOR_TOP = levelTileImageDictionary.get(1+levelType, 11);
		this.CASTLE_DOOR_TOP = levelTileImageDictionary.get(1+levelType, 12);
		this.CASTLE_DOOR = levelTileImageDictionary.get(1+levelType, 13);
		this.COIN = itemTileImageDictionary.get(6+levelType, 0);
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
					// Ground Object
					if (type == 'G')
					{	
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.GROUND));
						this.levelTiles.add(this.GROUND);
					}
					//Brick Object
					else if (type == 'B')
					{
						this.allLevelObjects.add(new Brick(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.BRICK));
						this.levelTiles.add(this.BRICK);
					}
					// Question Mark Box
					else if (type == 'Q')
					{
						this.allLevelObjects.add(new QuestionMarkBox(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.QUESTION_MARK_BOX));
						this.levelTiles.add(this.QUESTION_MARK_BOX);
					}
					// Metal Box
					else if (type == 'A')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), 
								new Dimension(this.imageWidth, this.imageHeight), true, this.METAL_BOX));
						this.levelTiles.add(this.METAL_BOX);
					}
					// Top Left piece of Pipe
					else if (type == 'I')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.TOP_LEFT_PIPE));
						this.levelTiles.add(this.TOP_LEFT_PIPE);
					}
					// Top right piece of Pipe
					else if (type == 'O')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.TOP_RIGHT_PIPE));
						this.levelTiles.add(this.TOP_RIGHT_PIPE);
					}
					// Left section of Pipe
					else if (type == 'K')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.LEFT_PIPE));
						this.levelTiles.add(this.LEFT_PIPE);
					}
					// Right section of Pipe
					else if (type == 'L')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.RIGHT_PIPE));
						this.levelTiles.add(this.RIGHT_PIPE);
					}
					// Immovable Beveled Brick
					else if (type == 'H')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.BEVELED_BRICK));
						this.levelTiles.add(this.BEVELED_BRICK);
					}
					// Segment of Pole
					else if (type == 'P')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.POLE));
						this.levelTiles.add(this.POLE);
					}
					// Top ball of flagpole
					else if (type == 'S')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.TOP_POLE));
						this.levelTiles.add(this.TOP_POLE);
					}
					// Top of the castle piece
					else if (type == 'C')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_TOP));
						this.levelTiles.add(this.CASTLE_TOP);
					}
					// Left castle window.
					else if (type == 'E')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_WINDOW_LEFT));
						this.levelTiles.add(this.CASTLE_WINDOW_LEFT);
					}
					// Center castle window
					else if (type == 'D')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_WINDOW_CENTER));
						this.levelTiles.add(this.CASTLE_WINDOW_CENTER);
					}
					// Right castle window
					else if (type == 'M')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_WINDOW_RIGHT));
						this.levelTiles.add(this.CASTLE_WINDOW_RIGHT);
					}
					// Castle interior parapet
					else if (type == 'N')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_INTERIOR_TOP));
						this.levelTiles.add(this.CASTLE_INTERIOR_TOP);
					}
					// Top of castle door
					else if (type == 'F')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_DOOR_TOP));
						this.levelTiles.add(this.CASTLE_DOOR_TOP);
					}
					// Castle door piece
					else if (type == 'J')
					{
						this.allLevelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.CASTLE_DOOR));
						this.levelTiles.add(this.CASTLE_DOOR);
					}
					// A coin.
					else if (type == '1')
					{
						this.allLevelObjects.add(new Coin(new Point(x*this.imageWidth, y*this.imageHeight),
								new Dimension(this.imageWidth, this.imageHeight), true, this.COIN));
						this.itemTiles.add(this.COIN);
					}
					/*
					 * If the given character cannot be found, simply create a null filler for the "map".
					 */
					else
					{
						this.levelTiles.add(null);
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
	
	/*
	 *  Updates the Array containing all of the on-screen objects.
	 *  Objects are taken from the Array that contains every level piece.
	 *  	They are added to this Array when they are within the Screen bounds
	 *  	and removed when they are past the edge of the Screen.
	 */
	public void updateOnScreenObjects(){
		for (LevelObject ob : allLevelObjects) {
			if (ob.getOriginalLocation().x + GLOBAL_OFFSET < 700 && !levelObjects.contains(ob)){
				// Add object to Array when within a few pixels of the right edge of the screen.
				levelObjects.add(ob);
			}
			if (ob.getOriginalLocation().x + GLOBAL_OFFSET < 0- this.getImageWidth() && levelObjects.contains(ob)){
				// Remove object from active Array when it passes the left side of the Screen.
				levelObjects.remove(ob);
			}
		}
	}

	
	/**
	 * @return The Global Offset of the Level.
	 */
	public void setGlobalOffset(int gblOffset) {
		this.GLOBAL_OFFSET = gblOffset;
	}

	/**
	 * @return The type of Level, in integer format.
	 */
	public int getLevelType() {
		return levelType;
	}

	/**
	 * @param levelType Change the levelType, in integer format.
	 */
	public void setLevelType(int levelType) {
		this.levelType = levelType;
		this.setLevelConstants(levelType);
	}
	/**
	 * @return The Array containing all of the static Level tiles.
	 */
	public ArrayList<BufferedImage> getLevelTiles() {
		return levelTiles;
	}
	/**
	 * Get a specific tile from the Level tiles Array.
	 * 
	 * @param x The x coordinate of the tile to get.
	 * @param y The y coordinate of the tile to get.
	 * @return The image at the specified tile location.
	 */
	public BufferedImage getTile(int x, int y){
		return levelTiles.get(y*this.getWidth() + (x));
	}
	
	/**
	 * Get a specific tile from the Level tiles Array.
	 * 
	 * @param i The index of the tile to get.
	 * @return The image at the specified tile location.
	 */
	public BufferedImage getTile(int i){
		return levelTiles.get(i);
	}
	/**
	 * @return The Array of all Actors.
	 */
	public ArrayList<Actor> getActors() {
		return actors;
	}
	/**
	 * @return The Array of all Level objects.
	 */
	public ArrayList<LevelObject> getLevelObjects() {
		return levelObjects;
	}
	/**
	 * @return The width of the Level in number of tiles.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width The width of the Level in tiles.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return The height of the Level in number of tiles.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height The height of the Level in tiles.
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return The height of the images in the Array.
	 */
	public int getImageHeight() {
		return imageHeight;
	}
	/**
	 * @param imageHeight Set the height of the images in the array.
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	/**
	 * @return The width of the images in the Array.
	 */
	public int getImageWidth() {
		return imageWidth;
	}
	/**
	 * @param imageWidth Set the width of the images in the array.
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	/**
	 * @return The overall global offset of the Level.
	 */
	public int getGlobalOffset() {
		return GLOBAL_OFFSET;
	}
}

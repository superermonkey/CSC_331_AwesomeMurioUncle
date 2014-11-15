import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Level{
	//private BufferedImage[][] tiles;
	private ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
	private ImageArray tileImageDictionary = new ImageArray();
	
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>();
	
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
	
	private Point levelOffset = new Point(0,0);
	protected Point globalOffset = new Point (0,0);
	private int width;
	private int height;
	private int imageHeight = 32;
	private int imageWidth = 32;
	
	public Level(){
		
	}
	
	public Level(int w, int h, int lvlType, String mapFileName, ImageArray tileImageDictionary){
		this.width = w;
		this.height = h;
		this.tileImageDictionary = tileImageDictionary;
		this.setLevelConstants(lvlType);
		this.readMap(mapFileName);
	}
	

	private void setLevelConstants(int levelType){
		this.GROUND = tileImageDictionary.get(0, 0+levelType);
		this.BRICK = tileImageDictionary.get(1, 0+levelType);
		this.METAL_BOX = tileImageDictionary.get(3, 0+levelType);
		this.QUESTION_MARK_BOX = tileImageDictionary.get(24, 0+levelType);
		this.TOP_LEFT_PIPE = tileImageDictionary.get(0, 8+levelType);
		this.TOP_RIGHT_PIPE = tileImageDictionary.get(1, 8+levelType);
		this.LEFT_PIPE = tileImageDictionary.get(0, 9+levelType);
		this.RIGHT_PIPE = tileImageDictionary.get(1, 9+levelType);
		this.BEVELED_BRICK = tileImageDictionary.get(0, 1+levelType);
		this.POLE = tileImageDictionary.get(16, 9+levelType);
		this.TOP_POLE = tileImageDictionary.get(16, 8+levelType);
	}
	/**\
	 * readMap reads the level in from a text file and creates the corresponding Level object.
	 * It then assigns the correct image to each tile and adds the tiles to the Level.
	 * 
	 * @param fileName The text file for the level to be loaded from.
	 * @return The level object with tiles added.
	 */
	private void readMap(String fileName){
		ArrayList<String> lines = new ArrayList<String>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			int widest = 0;
			while (true) {
				String line = reader.readLine();
				if (line == null){
					break;
				}
				if(!line.startsWith("#")){     //ignore comments in level text file
					lines.add(line);
	
					if (line.length() > widest){ 
						widest = line.length();
					}
				}
			}
			reader.close();
			
			this.setWidth(widest);
			this.setHeight(lines.size());
			
			for (int y = 0; y < height; y++){
				String line = lines.get(y);
				for (int x = 0; x <line.length(); x++){
					char type = line.charAt(x);  
					if (type == 'G'){
						this.levelObjects.add(new StaticObject(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.GROUND));
						this.tiles.add(this.GROUND);
					}
					else if (type == 'B'){
						this.levelObjects.add(new Brick(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.BRICK));
						this.tiles.add(this.BRICK);
					}
					else if (type == 'Q'){
						this.levelObjects.add(new QuestionMarkBox(new Point(x*this.imageWidth, y*this.imageHeight), new Dimension(this.imageWidth, this.imageHeight), true, this.QUESTION_MARK_BOX));
						this.tiles.add(this.QUESTION_MARK_BOX);
					}
					else if (type == 'A'){
						this.tiles.add(this.METAL_BOX);
					}
					else if (type == 'I'){
						this.tiles.add(this.TOP_LEFT_PIPE);
					}
					else if (type == 'O'){
						this.tiles.add(this.TOP_RIGHT_PIPE);
					}
					else if (type == 'K'){
						this.tiles.add(this.LEFT_PIPE);
					}
					else if (type == 'L'){
						this.tiles.add(this.RIGHT_PIPE);
					}
					else if (type == 'H'){
						this.tiles.add(this.BEVELED_BRICK);
					}
					else if (type == 'P'){
						this.tiles.add(this.POLE);
					}
					else if (type == 'S'){
						this.tiles.add(this.TOP_POLE);
					}
					else
						//this.levelObjects.add(null);
						this.tiles.add(null);
				}
			}
			
			this.tiles.add(this.QUESTION_MARK_BOX);
		}
		
		catch(IOException e){
			System.out.println("Invalid Level Input File!! Level.readMap(String fileName) " + fileName);
		}
	}
	
	
	/**
	 * @return the levelOffset
	 */
	public Point getLevelOffset() {
		return levelOffset;
	}


	/**
	 * @param levelOffset the levelOffset to set
	 */
	public void setLevelOffset(Point lvlOffset) {
		for (int i=0; i < levelObjects.size(); i++){
			levelObjects.get(i).setLevelOffset(lvlOffset);
		}
		if (lvlOffset.getX() < this.getWidth() && lvlOffset.getY() < this.getHeight()){
			this.levelOffset = lvlOffset;

		}
	}
	
	public void setGlobalOffset(Point gblOffset) {
		for (int i=0; i < levelObjects.size(); i++){
			levelObjects.get(i).setGlobalOffset(gblOffset);
		}
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
		return globalOffset;
	}



}

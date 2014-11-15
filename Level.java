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
	private BufferedImage GROUND;
	private BufferedImage BRICK;
	private BufferedImage METAL_BOX;
	private BufferedImage QUESTION_MARK_BOX;
	private BufferedImage TOP_LEFT_PIPE;
	private BufferedImage TOP_RIGHT_PIPE;
	private BufferedImage LEFT_PIPE;
	private BufferedImage RIGHT_PIPE;
	
	private Point levelOffset = new Point(0,0);
	private int width = 20;
	private int height = 16;
	
	public Level(int w, int h, int lvlType, String mapFileName, ImageArray tileImageDictionary){
		this.width = w;
		this.height = h;
		this.tileImageDictionary = tileImageDictionary;
		this.setLevelConstants(lvlType);
		this.readMap(mapFileName);
	}
	

	private void setLevelConstants(int levelType){
		this.GROUND = tileImageDictionary.get(0, 0);
		this.BRICK = tileImageDictionary.get(1, 0);
		this.METAL_BOX = tileImageDictionary.get(4, 0);
		this.QUESTION_MARK_BOX = tileImageDictionary.get(24, 0);
		this.TOP_LEFT_PIPE = tileImageDictionary.get(0, 8);
		this.TOP_RIGHT_PIPE = tileImageDictionary.get(1, 8);
		this.LEFT_PIPE = tileImageDictionary.get(0, 9);
		this.RIGHT_PIPE = tileImageDictionary.get(1, 9);
				
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
						this.tiles.add(this.GROUND);
					}
					else if (type == 'B'){
						this.tiles.add(this.BRICK);
					}
					else if (type == 'Q'){
						this.tiles.add(this.QUESTION_MARK_BOX);
					}
					else if (type == 'H'){
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
					
					else
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
		if (lvlOffset.getX() < this.getWidth() && lvlOffset.getY() < this.getHeight()){
			this.levelOffset = lvlOffset;
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

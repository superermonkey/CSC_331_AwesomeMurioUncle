import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Level extends Screen{
	private BufferedImage[][] tiles;
	private ArrayList<Actor> actors;
	private BufferedImage GROUND;
	private BufferedImage BRICK;
	private BufferedImage METAL_BOX;
	private BufferedImage QUESTION_MARK_BOX;
	
	public Level(int width, int height, int lvlType, String mapFileName){
		tiles = new BufferedImage[width][height];
		actors = new ArrayList<Actor>();
		this.setLevelConstants(lvlType);
		this.readMap(mapFileName);
	}
	
	public int getWidth(){
		return tiles.length;
	}
	
	public int getHeight(){
		return tiles[0].length;
	}
	
	public void setTile(int x, int y, BufferedImage tile) throws IOException{
		tiles[x][y] = tile;
	}
	
	public Image getTile(int x, int y) {
		return null;
		
	}
	
	private void setLevelConstants(int levelType){
		this.GROUND = tileImages.getImages().get((levelType * tileImages.getRows()) + 0);
		this.BRICK = tileImages.getImages().get((levelType * tileImages.getRows()) + 2);
		this.METAL_BOX = tileImages.getImages().get((levelType * tileImages.getRows()) + 3);;
		this.QUESTION_MARK_BOX = tileImages.getImages().get((levelType * tileImages.getRows()) + 23);;
		
		switch(levelType){
		//Basic Level Type

		case 1: {
			break;
		}
		// Dark Level type
		case 2: {
			break;
		}
		//Snow Level Type
		case 3:{
			break;
		}
		// Green Level Type
		case 4:{
			break;
		}
		default:{
			break;
		}
		
		}
	}
	/**\
	 * readMap reads the level in from a text file and creates the corresponding Level object.
	 * It then assigns the correct image to each tile and adds the tiles to the Level.
	 * 
	 * @param fileName The text file for the level to be loaded from.
	 * @return The level object with tiles added.
	 * @throws IOException
	 */
	private void readMap(String fileName){
		ArrayList<String> lines = new ArrayList<String>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String widest = "";
			while (true) {
				String line = reader.readLine();
				if (line == null){
					break;
				}
				if(!line.startsWith("#")){     //ignore comments in level text file
					lines.add(line);
	
					if (line.length() > widest.length()){ 
						widest = line;
					}
				}
			}
			int width = Integer.parseInt(widest);
			int height = lines.size();
			
			
			for (int y = 0; y < height; y++){
				String line = lines.get(y);
				for (int x = 0; x <line.length(); x++){
					char type = line.charAt(x);  
					if (type == 'G'){
						this.tiles[x][y] = this.GROUND;
					}
					else if (type == 'B'){
						this.tiles[x][y] = this.BRICK;
					}
					else if (type == 'Q'){
						this.tiles[x][y] = this.QUESTION_MARK_BOX;
					}
					else if (type == 'K'){
						this.tiles[x][y] = this.METAL_BOX;
					}
				}
			}
		}
		catch(IOException e){
			System.out.println("Invalid Level Input File!! Level.readMap(String fileName) " + fileName);
		}
	}
	
	

}

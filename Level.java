import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Level {
	private Image[][] tiles;
	private ArrayList<Actor> actors;
	
	public Level(int width, int height){
		tiles = new Image[width][height];
		actors = new ArrayList<Actor>();
	}
	
	public int getWidth(){
		return tiles.length;
	}
	
	public int getHeight(){
		return tiles[0].length;
	}
	
	public void setTile(int x, int y, Image tile) throws IOException{
		tiles[x][y] = tile;
	}
	
	public Image getTile(int x, int y) {
		return null;
		
	}
	
	/**\
	 * readMap reads the level in from a text file and creates the corresponding Level object.
	 * It then assigns the correct image to each tile and adds the tiles to the Level.
	 * 
	 * @param fileName The text file for the level to be loaded from.
	 * @return The level object with tiles added.
	 * @throws IOException
	 */
	private Level readMap(String fileName) throws IOException{
		ArrayList<String> lines = new ArrayList<String>();
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
		
		Level newLvl = new Level(width, height);
		
		
		for (int y = 0; y < height; y++){
			String line = lines.get(y);
			for (int x = 0; x <line.length(); x++){
				char type = line.charAt(x);  
				if (type == 'G'){
					//set tile images from sprite sheet processing class here
				}
			}
		}
		return newLvl;
	}

}

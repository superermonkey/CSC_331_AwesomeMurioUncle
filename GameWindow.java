import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author Monkey
 *
 */

public class GameWindow extends JFrame{

	/**
	 * Creates a window that contains the entire game.
	 */
	public GameWindow() {
		setTitle("Awesome Murio Uncle!");
		Screen screen = new Screen();
		add(screen);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	}
	/**
	 * main method contains window builder.
	 * @param args
	 */
	public static void main(String[] args) {
		GameWindow window = new GameWindow();

	}

}

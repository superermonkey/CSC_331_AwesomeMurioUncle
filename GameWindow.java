import javax.swing.JFrame;
/**
 * @author RyanPierce
 *
 */

/**
 * Create the window for the game to exist in.
 * 
 * @author RyanPierce
 *
 */
public class GameWindow extends JFrame{
	
	private static final long serialVersionUID = -7357480200628684109L;
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
	 * The main method for the GameWindow.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		new GameWindow();
	}
}

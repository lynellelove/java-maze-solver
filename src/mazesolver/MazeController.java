package mazesolver;

/**
 * Facilitates the execution of the MazeModel and MazeView classes.
 * 
 * @author Lynelle Love
 */
public class MazeController {

	/**
	 * Executes the program.
	 * @param args
	 */
	public static void main(String[] args) {
		MazeModel model = new MazeModel();
		MazeView view = new MazeView(model);
		
		view.showGUI();
        view.drawMaze();
        model.solveMaze();
        view.pause(1);
        view.drawSolution();
        view.done();   
	}

}
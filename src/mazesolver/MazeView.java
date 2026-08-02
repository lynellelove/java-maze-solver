package mazesolver;

import edu.princeton.cs.algs4.StdDraw;

/**
 * Uses StdDraw to display a generated maze and the maze's solution.
 * 
 * @author Lynelle Love
 */
public class MazeView{
	private final int cols, rows;   // dimension of maze
	private MazeModel model ; 
	private int height, width;
	private int startVertex, finishVertex;

	/**
	 * Instantiates the MazeView object.
	 * @param model	the model to draw
	 */
	public MazeView(MazeModel model) {
		this.startVertex = model.getStartVertex();
		this.finishVertex = model.getFinishVertex();
		this.model = model;
		this.cols = model.getRealColumns();
		this.rows = model.getRealRows();
		this.height = 600;
		this.width = (int) Math.round(1.0 * height * cols / rows);
	}
	
	/**
	 * Sets basic information about the gui and displays the empty frame.
	 */
	public void showGUI() {
		StdDraw.setCanvasSize(width, height);

		StdDraw.setXscale(0, cols * 1.2);
		StdDraw.setYscale(0, rows * 1.2);
		
		StdDraw.setTitle("MAZE SOLVER");				
	}

	/**
	 * Draws the maze using information from the model. Colors all cells that are marked as walls black,
	 * colors the start vertex green, and colors the finish vertex black. Additionally, it displays
	 * text at the bottom of the frame that narrates the process.
	 */
	public void drawMaze() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(13.5, 2, "A generated maze with " + (rows - 2) + " rows and " 
				+ (cols - 2) + " columns will draw in:");
		StdDraw.show();
		
		pause(1);
		countdown(3);
		
		clearText();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(13.5, 2, "The generated maze is being drawn...");
		
		double squareSize = ((height / rows) < (width / cols)) ? (height / rows) : (width / cols);
		double halfLength = rows / 2 * squareSize / ((height < width) ? height : width);
		double y = halfLength + halfLength * rows * 2.25;

		int cell = 0;
		
		for (int j = 0; j < rows; j++) {
			double x = 3.5;
			for (int i = 0; i < cols; i++) {				
				if (cell == startVertex) {
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.filledSquare(x, y, halfLength);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(x, y, "S");
				}
				else if (cell == finishVertex){
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledSquare(x, y, halfLength);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(x, y, "F");
				}
				else if (model.getWalls()[cell]) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledSquare(x, y, halfLength);
				}
				
				x = x + (halfLength * 2);
				cell++;
			}
			y = y - (halfLength * 2);
			StdDraw.show();
			StdDraw.pause(50);
		}
		
	}

	/**
	 * Draws the maze solution using information about the model. The solution path is
	 * drawn with grey circles and short pauses to make it more visually interesting.
	 * Additionally, it displays text at the bottom of the frame that narrates the process.
	 */
	public void drawSolution() {
		clearText();
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(13.5, 2, "The maze will solve in:");
		StdDraw.show();
		
		pause(1);
		countdown(3);
		
		clearText();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(13.5, 2, "The maze has been solved.");
		pause(1);
		
		clearText();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(13.5, 2, "The maze solution is being drawn...");
		
		double squareSize = ((height / rows) < (width / cols)) ? (height / rows) : (width / cols);
		double halfLength = rows / 2 * squareSize / ((height < width) ? height : width);
		double x = 3.5 + halfLength * 2;
		double y = (halfLength + halfLength * rows * 2.25) - (halfLength * 2);
		
		Comparable[] path = model.getSolutionPath();
		int[] solCells = new int[path.length];
		
		for (int i = 0; i < path.length; i++) {
			solCells[i] = (int) path[i];
		}
		
		StdDraw.setPenColor(StdDraw.GRAY);
		for (int i = 0; i < solCells.length; i++) {
			if (i != 0) {
				if (solCells[i - 1] == solCells[i] - 1) {
					x = x + (halfLength * 2);
				}
				else if (solCells[i - 1] == solCells[i] + 1) {
					x = x - (halfLength * 2);
				}
				else if (solCells[i - 1] == solCells[i] + cols) {
					y = y + (halfLength * 2);
				}
				else if (solCells[i - 1] == solCells[i] - cols) {
					y = y - (halfLength * 2);
				}
			}
			
			StdDraw.filledCircle(x, y, 0.2);
			StdDraw.show();
			StdDraw.pause(150);
		}
	}
	
	/**
	 * Displays a countdown starting at the specified integer.
	 * @param seconds	the number of seconds to countdown from
	 */
	public void countdown(int seconds) {
		
		for (Integer i = seconds; i >= 0; i--) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(13.5, 1, 0.25);
			
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(13.5, 1, i.toString());
			
			StdDraw.show();
			
			StdDraw.pause(1000);
		}
		
	}

	/**
	 * Pauses the execution of StdDraw for an n number of seconds.
	 * @param n	the number of seconds to pause StdDraw
	 */
	public void pause(int n) {
		StdDraw.pause(n * 1000);
		
	}

	/**
	 * Displays text to demonstrate that the program is done.
	 */
	public void done() {
		clearText();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(13.5, 2, "Maze complete.");
		StdDraw.show();
	}
	
	/**
	 * A helper method to clear the text at the set location by drawing a white 
	 * filled rectangle over the defined area.
	 */
	private void clearText() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(13.5, 1.5, 10, 1);
		StdDraw.show();
	}

}
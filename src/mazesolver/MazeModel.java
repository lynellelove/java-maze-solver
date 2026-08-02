package mazesolver;

import edu.princeton.cs.algs4.DepthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/** 
 * Generates a grid-style maze of 15 by 20 size and prepares the appropriate maze cells
 * for being used in other classes. Provides a method to solve the maze.
 *  
 * @author Lynelle Love
 *
 */
public class MazeModel {
		Graph graph;
		DepthFirstPaths dfp;
		Stack<Integer> stack = new Stack<>();
		int[] vertices;
		Comparable[] solutionPath;
		boolean[] visited, wall;
		int startVertex, finishVertex;
		int rows, realRows, columns, realColumns;
		int totalCells, totalMazeCells;
		int count;
		int maxStack;
		
		/**
		 * Instantiates the MazeModel object.
		 */
		public MazeModel() {
			this.rows = 15;
			this.columns = 20;
			realRows = rows + 2;
			realColumns = columns + 2;
			startVertex = columns + 3;
			totalMazeCells = rows * columns;
			totalCells = realRows * realColumns;
			graph = new Graph(totalCells);
			visited = new boolean[totalCells];
			wall = new boolean[totalCells];
			count = 0;
			maxStack = 0;
			
			generateMaze();
		}
		
		/**
		 * Initial method to populate the boolean arrays with the border cells.
		 */
		public void generateMaze() {
			for (int i = 0; i < visited.length; i++) {
				if (isBorderCell(i)) {
					wall[i] = true;
					visited[i] = true;
				}
			}
			
			generateMaze(startVertex, false);
		}
		
		/**
		 * Recursively generates a grid-style maze, where each wall has the same height and width as
		 * each maze path. The method uses a stack to backtrack so it can explore unvisited neighbors
		 * further back on the path. Before the recursive method call executes, a new edge using the 
		 * current cell and the next cell being visited is added to a previously instantiated graph for 
		 * use in solving the maze. Keeps track of the cell at each succeeding maximum stack size so 
		 * a finish vertex can be defined when the maze is being solved dynamically. Uses a helper 
		 * method to identify whether or not an unvisited neighbor is valid or not.
		 * 
		 * @param cell	the integer index of the current cell being generated
		 * @param isPop	boolean value identifying if the current cell is a stack pop
		 */
		public void generateMaze(int cell, boolean isPop) {
			stack.push(cell);
			
			// Identifies whether the current method call is a stack pop
			if (isPop == false) {
				if (stack.size() > maxStack) {
					maxStack = stack.size();
					finishVertex = cell;
				}
				markVisited(cell);
			}
			
			boolean neighborFound = false;
			
			int north = cell - realColumns;
			int south = cell + realColumns;
			int east = cell + 1;
			int west = cell - 1;
			
			// Looks for an unvisited neighbor.
			while ((!visited[north] || !visited[south] || !visited[east] || !visited[west]) && count <= totalMazeCells) {
				int r = (int) (Math.random() * 4);
				
				if (r == 0 && !visited[north] && isValidNeighbor(cell, north)) {
					neighborFound = true;
					graph.addEdge(cell, north);
					generateMaze(north, false);
				}
				else if (r == 1 && !visited[south] && isValidNeighbor(cell, south)) {
					neighborFound = true;
					graph.addEdge(cell, south);
					generateMaze(south, false);
				}
				else if (r == 2 && !visited[east] && isValidNeighbor(cell, east)) {
					neighborFound = true;
					graph.addEdge(cell, east);
					generateMaze(east, false);
				}
				else if (r == 3 && !visited[west] && isValidNeighbor(cell, west)) {
					neighborFound = true;
					graph.addEdge(cell, west);
					generateMaze(west, false);
				}
			}
			
			// If no neighbor has been found, pop the stack.
			if (neighborFound == false && count < totalMazeCells) {
				if (!stack.isEmpty()) {
					stack.pop();
					
					// Checks if stack is empty after being popped. Empty stack correlates to a finished maze.
					if (stack.isEmpty()) {
						fillWalls();
					}
					else {
						generateMaze(stack.pop(), true);
					}	
				}
			}
		}
		
		/**
		 * Helper method to checks whether the current cell is a valid neighbor to the previously 
		 * visited cell. A neighbor is defined as valid if it is not directly adjacent to any
		 * previously visited cells other than the one passed to this method. This preserves the 
		 * maze's grid design.
		 * 
		 * @param previousCell	the immediately previously visited cell
		 * @param cell	the current cell
		 * @return the result of whether or not the current cell is a valid neighbor
		 */
		private boolean isValidNeighbor(int previousCell, int cell) {			
			boolean result = true;
			
			int north = cell - realColumns;
			int south = cell + realColumns;
			int east = cell + 1;
			int west = cell - 1;
			
			if (north != previousCell && !isBorderCell(north) && !wall[north]) {
				if (visited[north]) {
					result = false;
				}
			}
			if (south != previousCell && !isBorderCell(south) && !wall[south]) {
				if (visited[south]) {					
					result = false;
				}
			}
			if (east != previousCell && !isBorderCell(east) && !wall[east]) {
				if (visited[east]) {
					result = false;
				}
			}
			if (west != previousCell && !isBorderCell(west) && !wall[west]) {
				if (visited[west]) {
					result = false;
				}
			}
			
			if (result == false) {
				markWall(cell);
				markVisited(cell);
			}
			
			return result;
		}
		
		/**
		 * Checks if the supplied cell index is a border cell.
		 * @param cell	the integer index of the cell
		 * @return	the boolean results of whether or not the cell is a border cell
		 */
		private boolean isBorderCell(int cell) {
			if (cell < startVertex) {
				return true;
			}
			if (cell % realColumns == 0) {
				return true;
			}
			if ((cell + 1) % realColumns == 0) {
				return true;
			}
			if (cell >= totalCells - columns - 3) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Marks the supplied cell index as a wall.
		 * @param cell	the integer index to be marked as a wall
		 */
		private void markWall(int cell) {
			wall[cell] = true;
		}
		
		/**
		 * Marks all unvisited cells if walls if they have not already
		 * been marked as walls.
		 */
		private void fillWalls() {
			for (int i = 0; i < totalCells; i++) {
				if (!visited[i] && !wall[i]) {
					visited[i] = true;
					wall[i] = true;
					count++;
				}
			}
		}
		
		/**
		 * Marks the supplied cell index as visited and increases the count
		 * of cells already visited.
		 * @param cell
		 */
		private void markVisited(int cell) {
			if (!visited[cell]) {
				visited[cell] = true;
				count++;
			}
		}
		
		/**
		 * Solves the maze using a DepthsFirstPaths class of algs4.jar and queues the
		 * solution path. It then copies the solution path from the queue to a newly
		 * defined int array that can be used in other classes.
		 */
		public void solveMaze() {
			dfp = new DepthFirstPaths(graph, startVertex);
			Queue<Integer> queue = new Queue<Integer>();
			
			for (Integer integer: dfp.pathTo(finishVertex)) {
				queue.enqueue(integer);
			}
			
			solutionPath = new Comparable[queue.size()];

			for (int i = 0; i < solutionPath.length; i++) {
				solutionPath[i] = queue.dequeue();
			}
		}
		
		public Comparable[] getSolutionPath() {
			return solutionPath;
		}
		
		public int getFinishVertex() {
			return finishVertex;
		}
		
		public int getStartVertex() {
			return startVertex;
		}
		
		public boolean[] getWalls() {
			return wall;
		}
		
		public int getRealColumns() {
			return realColumns;
		}
		
		public int getRealRows() {
			return realRows;
		}

}

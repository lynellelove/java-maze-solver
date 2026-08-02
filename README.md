# Java Maze Solver

A Java maze generation and solving application taht uses graph traversal to generate a randomized grid-style maze and display its solution visually.

The project uses an MVC-style structure:

- `MazeModel` generates the maze, stores wall/path data, builds the graph, and solves the maze.
- `MazeView` displays the maze and solution using `StdDraw`.
- `MazeController` runs the application.

## Features

- Generates a randomized 15x20 grid-style maze
- Represents maze cells as vertices in a graph
- Uses Princeton's `algs4` library for graph, stack, queue, and depth-first path utilities
- Selects a start and finish vertex
- Solves the generated maze using depth-first search
- Displays the generated maze and animated solution path with `StdDraw`

## Technologies Used

- Java
- Princeton `algs4`
- Graph traversal
- Depth-first search
= StdDraw visualization

## Project Structure

```text
.
├── src/
│   └── mazesolver/
│       ├── MazeController.java
│       ├── MazeModel.java
│       └── MazeView.java
├── lib/
│   └── .gitkeep
├── .gitignore
└── README.md
```

## Dependency

This project uses Princeton's `algs4.jar` library for graph utilities and `StdDraw` visualization.

Download `algs4.jar` from Princeton's Algorithms, 4th Edition code page and place it in the local `lib/` folder:

[Princeton Algorithms, 4th Edition — Java libraries](https://algs4.cs.princeton.edu/code/)

`lib/algs4.jar`

The jar file is intentionally not committed to this repository. 

## How to Run

### macOS/Linux/WSL

Compile:

```text
mkdir -p out
```

```text
javac -cp "lib/algs4.jar" -d out src/mazesolver/*.java
```

Run:

```text
java -cp "out:lib/algs4.dir" mazesolver.MazeController
```

### Windows Powershell

Compile:

```text
mkdir out
```

```text
javac -cp "lib/algs4.jar" -d out src/mazesolver/*.java
```

Run:

```text
java -cp "out;lib/algs4.jar" mazesolver.MazeController
```

## Notes

This project was originally developed in 2023 as a Java coursework project and was later cleaned up for portfolio presentation.

The application uses StdDraw, so running it opens a graphical window that displays the generated maze and then animates the solution path.

## What I Learned

This project reinforced several computer science and software development concepts:

- Representing a grid-based problem as a graph
- Using depth-first search to find a path through a graph
- Separating program responsibilities with model, view, and controller classes
- Working with external Java libraries
- Building a small graphical Java application
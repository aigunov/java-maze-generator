package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import backend.academy.labirints.model.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConsoleRender implements Render{
    private final static String PASSAGE = "⬜️";
    private final static String WALL = "⬛";
    private final static String BAD = "⛔";
    private final static String GOOD = "✅";
    private final static String START = "🟢";
    private final static String FINISH = "🛑";
    private final static String PATH = "🟪";

    private int width, height;
    private Cell[][] labyrinth;
    private Map<Cell, List<Cell>> adjacentCells;
    private String[][] grid;

    @Override
    public void renderLabyrinth(final Maze maze, final Point start, final Point finish) {
        labyrinth = maze.maze();
        adjacentCells = maze.adjacentCells();
        width = maze.maze()[0].length;
        height = maze.maze().length;
        grid = new String[height * 2 + 1][width * 2 + 1];

        Arrays.fill(grid[0], WALL);
        Arrays.fill(grid[height * 2], WALL);

        for (int i = 1; i < height * 2; i++) {
            Arrays.fill(grid[i], PASSAGE);
        }

        for (int i = 1; i < height * 2; i++) {
            grid[i][0] = WALL;
            grid[i][width * 2] = WALL;
        }

        for (int i = 0; i < maze.maze().length; i++) {
            for (int j = 0; j < maze.maze()[0].length; j++) {
                var grid_row = i * 2 + 1;
                var grid_col = j * 2 + 1;
                var cell = labyrinth[i][j];
                grid[grid_row][grid_col] = switch (cell.type()){
                    case Cell.CellType.GOOD -> GOOD;
                    case Cell.CellType.BAD -> BAD;
                    case Cell.CellType.NOTHING -> PASSAGE;
                };
                grid[grid_row + 1][grid_col + 1] = WALL;
                for(var neigh: maze.walls().get(cell)){
                    if (cell.x()+1 == neigh.x() && cell.y() == neigh.y()){
                        grid[grid_row][grid_col + 1] = WALL;
                    }
                    if(cell.x() == neigh.x() && cell.y() + 1 == neigh.y()){
                        grid[grid_row + 1][grid_col] = WALL;
                    }
                }
            }
        }

        grid[(start.y() - 1) * 2 + 1][(start.x() - 1) * 2 + 1] = START;
        grid[(finish.y() - 1) * 2 + 1][(finish.x() - 1) * 2 + 1] = FINISH;

        draw();
    }

    @Override
    public void renderPathInLabyrinth(List<Cell> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            var cell = path.get(i);
            var grid_row = cell.y() * 2 + 1;
            var grid_col = cell.x() * 2 + 1;
            if (!grid[grid_row][grid_col].equals(START)) {
                grid[grid_row][grid_col] = PATH;
            }
            var nextCell = path.get(i + 1);
            switch (cell.getRelativePosition(nextCell)) {
                case 1 -> grid[grid_row - 1][grid_col] = PATH;
                case 2 -> grid[grid_row][grid_col + 1] = PATH;
                case 3 -> grid[grid_row + 1][grid_col] = PATH;
                case 4 -> grid[grid_row][grid_col - 1] = PATH;
            }
        }
        draw();
    }

    @Override
    public void draw() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

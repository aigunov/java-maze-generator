package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import backend.academy.labirints.model.Point;
import java.util.Arrays;

public class ConsoleRender implements Render{
    private final static String PASSAGE = "⬜️";
    private final static String WALL = "⬛";
    private final static String BAD = "⛔";
    private final static String GOOD = "✅";
    private final static String START = "🟢";
    private final static String FINISH = "🛑";

    private int width, height;
    private Cell[][] labyrinth;

    private String[][] grid;

    @Override
    public void render(final Maze maze, final Point start, final Point finish) {
        labyrinth = maze.maze();
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

        grid[(start.x() - 1) * 2 + 1][(start.y() - 1) * 2 + 1] = START;
        grid[(finish.x() - 1) * 2 + 1][(finish.y() - 1) * 2 + 1] = FINISH;

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

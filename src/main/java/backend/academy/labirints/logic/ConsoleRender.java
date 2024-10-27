package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.Arrays;


public class ConsoleRender implements Render{
    private static Render instance;

    private final static String PASSAGE = "⬜️";
    private final static String WALL = "⬛";
    private final static String BAD = "⛔";
    private final static String GOOD = "✅";
    private final static String START = "🟢";
    private final static String FINISH = "🛑";
    private final static String PATH = "🟪";

    private final String[][] grid;

    public static Render getInstance(int height, int width) {
        if (instance == null) {
            instance = new ConsoleRender(height, width);
        }
        return instance;
    }

    public ConsoleRender(int height, int width) {
        this.grid = new String[height * 2 + 1][width * 2 + 1];
        Arrays.stream(grid).forEach(row -> Arrays.fill(row, WALL));

        for (int i = 1; i < height * 2; i++) {
            Arrays.fill(grid[i], PASSAGE);
        }

        for (int i = 1; i < height * 2; i++) {
            grid[i][0] = WALL;
            grid[i][width * 2] = WALL;
        }
    }

    @Override
    public Render renderLabyrinth(final Maze maze) {
        var labyrinth = maze.maze();

        for (int i = 0; i < maze.maze().length; i++) {
            for (int j = 0; j < maze.maze()[0].length; j++) {
                var cell = labyrinth[i][j];
                var grid_row = cell.coordinates().calculateGridY();
                var grid_col = cell.coordinates().calculateGridX();
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

        grid[maze.start().y() * 2 + 1][maze.start().x() * 2 + 1] = START;
        grid[maze.finish().y() * 2 + 1][maze.finish().x() * 2 + 1] = FINISH;

        return instance;
    }

    @Override
    public Render renderPath(final Maze maze) {
        System.out.println("ПУТЬ: ");
        for (int i = 0; i < maze.path().size() - 1; i++) {
            var cell = maze.path().get(i);
            var nextCell = maze.path().get(i + 1);
            var grid_row = cell.coordinates().calculateGridY();
            var grid_col = cell.coordinates().calculateGridX();
            if (!grid[grid_row][grid_col].equals(START) && !grid[grid_row][grid_col].equals(FINISH)) {
                grid[grid_row][grid_col] = PATH;
            }
            //TODO хуйня, переделай по новой, чоза проверка на 1 2 3 4
            switch (cell.coordinates().getRelativePosition(nextCell.coordinates())) {
                case 1 -> grid[grid_row - 1][grid_col] = PATH;
                case 2 -> grid[grid_row][grid_col + 1] = PATH;
                case 3 -> grid[grid_row + 1][grid_col] = PATH;
                case 4 -> grid[grid_row][grid_col - 1] = PATH;
            }
        }
        return instance;
    }

    @Override
    public void draw() {
        for (String[] strings : grid) {
            for (String string : strings) {
                System.out.print(string + "\t");
            }
            System.out.println();
        }
    }
}

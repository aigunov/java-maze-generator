package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Arrays;
import lombok.Getter;

/**
 * Класс создающий графическое представления лабиринта и выводящий его в консоль
 */
@SuppressWarnings({"RegexpSinglelineJava"})
@SuppressFBWarnings({"CLI_CONSTANT_LIST_INDEX"})
@Getter
public class ConsoleRender implements Render {
    private final static String PASSAGE = "⬜️";
    private final static String WALL = "⬛";
    private final static String BAD = "⛔";
    private final static String GOOD = "✅";
    private final static String START = "🟢";
    private final static String FINISH = "🛑";
    private final static String PATH = "🟪";
    private static Render instance;
    private final String[][] grid;

    /**
     * Изначальная инициализация лабиринта(grid)
     * верхняя, нижняя, правая, левая - стенки WALL
     * вся внутрення часть - PASSAGE
     */
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

    public static Render getInstance(int height, int width) {
        if (instance == null) {
            instance = new ConsoleRender(height, width);
        }
        return instance;
    }

    /**
     * Метод создает лабиринт в grid[height*2+1][width*2+1]
     * Помечает клетки grid GOOD, BAD, WALL, START, FINISH в зависимости от значений в maze
     *
     * @param maze - информация о лабиринте
     * @return - instance ConsoleRender чтобы было удобнее работать и вызывать последовательность методов
     */
    @Override
    public Render renderLabyrinth(final Maze maze) {
        var labyrinth = maze.maze();

        for (int i = 0; i < maze.maze().length; i++) {
            for (int j = 0; j < maze.maze()[0].length; j++) {
                var cell = labyrinth[i][j];
                var gridRow = cell.coordinates().calculateGridY();
                var gridCol = cell.coordinates().calculateGridX();
                grid[gridRow][gridCol] = switch (cell.type()) {
                    case Cell.CellType.GOOD -> GOOD;
                    case Cell.CellType.BAD -> BAD;
                    case Cell.CellType.NOTHING -> PASSAGE;
                };
                grid[gridRow + 1][gridCol + 1] = WALL;
                for (var neigh : maze.walls().get(cell)) {
                    if (cell.x() + 1 == neigh.x() && cell.y() == neigh.y()) {
                        grid[gridRow][gridCol + 1] = WALL;
                    }
                    if (cell.x() == neigh.x() && cell.y() + 1 == neigh.y()) {
                        grid[gridRow + 1][gridCol] = WALL;
                    }
                }
            }
        }

        grid[maze.start().y() * 2 + 1][maze.start().x() * 2 + 1] = START;
        grid[maze.finish().y() * 2 + 1][maze.finish().x() * 2 + 1] = FINISH;

        return instance;
    }

    /**
     * Уже в созданном grid через метод {@link #renderLabyrinth(Maze)}
     * Прокладывает в нем путь помечая необходимые клетки символом PATH
     *
     * @param maze - информация о лабиринте
     * @return - instance ConsoleRender чтобы было удобнее работать и вызывать последовательность методов
     */
    @Override
    public Render renderPath(final Maze maze) {
        System.out.println("ПУТЬ: ");
        for (int i = 0; i < maze.path().size() - 1; i++) {
            var cell = maze.path().get(i);
            var nextCell = maze.path().get(i + 1);
            var gridRow = cell.coordinates().calculateGridY();
            var gridCol = cell.coordinates().calculateGridX();
            if (!START.equals(grid[gridRow][gridCol]) && !FINISH.equals(grid[gridRow][gridCol])) {
                grid[gridRow][gridCol] = PATH;
            }
            var coordinates = cell.coordinates().getRelativePosition(nextCell.coordinates());
            grid[coordinates.y()][coordinates.x()] = PATH;
        }
        return instance;
    }

    /**
     * Выводит grid в консоль
     */
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

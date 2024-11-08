package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import backend.academy.labirints.model.Maze;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Класс Generator - базовый класс генерации лабиринта содержащий
 * в себе одинаковую для двух алгоритмов генерации логику и поля
 * необходимые для генерации лабиринта
 */
@SuppressFBWarnings({"CLI_CONSTANT_LIST_INDEX"})
@SuppressWarnings({"MagicNumber"})
public abstract class Generator {
    protected final Random random = new SecureRandom();
    protected final Cell[][] maze;
    protected final Map<Cell, List<Cell>> adjacency = new HashMap<>();
    protected final Map<Cell, List<Cell>> walls = new HashMap<>();
    protected int width;
    protected int height;
    private final int[][] directions = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    /**
     * Конструктор инициализирующий все клетки лабиринта
     */
    protected Generator(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.maze = new Cell[height][width];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                var cell = Cell.builder()
                    .coordinates(new Cell.Coordinates(j, i))
                    .type(Cell.CellType.NOTHING)
                    .isVisited(false)
                    .build();
                maze[i][j] = cell;
            }
        }
        createWallsMap();
    }

    /**
     * Метод находит всех соседей для клетки (стенки не считаются)
     *
     * @param cell - клетка список чьих соседей надо вернуть
     * @return список соседей вверху-справа-снизу-слева если там не границы лабиринта
     */
    protected List<Cell> getAllNeighbors(final Cell cell) {
        var neighbors = new ArrayList<Cell>();

        for (var direction : directions) {
            var newRow = cell.y() + direction[1];
            var newCol = cell.x() + direction[0];

            if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length) {
                neighbors.add(maze[newRow][newCol]);
            }
        }

        return neighbors;
    }

    /**
     * Метод находит список не посещенных соседей клеток используя метод {@link #getAllNeighbors(Cell)}
     * @param cell - клетка список чьих не посещенных соседей надо вернуть
     * @return список не посещенных соседей
     */
    protected List<Cell> getUnvisitedNeighbors(final Cell cell) {
        return getAllNeighbors(cell).stream()
            .filter(c -> !c.isVisited())
            .toList();
    }

    /**
     * Метод помечающий рандомным образом клетки лабиринта
     * одним из двух типов поверхности: 1.GOOD 2.BAD
     * по умолчанию во всех клетках стоит NOTHING
     */
    public void generateRandomCells() {
        var countOfRandomCells = calculateMarkedCells(maze.length, maze[0].length);
        for (int i = 0; i < countOfRandomCells; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            maze[y][x].type(random.nextInt(2) == 0 ? Cell.CellType.GOOD : Cell.CellType.BAD);
        }
    }

    /**
     * Метод высчитывает сколько клеток надо пометить
     * @param rows - количество рядов в лабиринте
     * @param cols - количество столбцов в лабиринте
     * @return число, обозначающее сколько клеток надо пометить случайным образом
     */
    public int calculateMarkedCells(int rows, int cols) {
        int totalCells = rows * cols;
        // Формула:
        //   = 0.35 - 0.2 множитель для определения желаемого диапазона
        //   = 0.25 - 0.1 множитель для определения случайного отклонения от желаемого диапазона
        double desiredRangeMultiplier = random.nextDouble() * (0.35 - 0.2) + 0.2;
        double randomDeviationMultiplier = random.nextDouble() * (0.25 - 0.1) + 0.1;
        int desiredCells = (int) (totalCells * desiredRangeMultiplier);
        int deviation = (int) (desiredCells * randomDeviationMultiplier);
        return Math.max(3, Math.min(desiredCells + deviation, totalCells - 3)); // Ограничиваем диапазон
    }

    /**
     * Метода добавляющий связь(проход) в adjacency и удаляет стенки в walls между клетками соседями cell, neighbour
     */
    protected void removeWall(final Cell cell, final Cell neighbour) {
        adjacency.computeIfAbsent(cell, k -> new ArrayList<>()).add(neighbour);
        adjacency.computeIfAbsent(neighbour, k -> new ArrayList<>()).add(cell);
        walls.getOrDefault(cell, new ArrayList<>()).remove(neighbour);
        walls.getOrDefault(neighbour, new ArrayList<>()).remove(cell);
    }

    /**
     * Надо создать лабиринт в котором типы поверхностей
     * могут влиять на выбранный алгоритмом путь
     * поскольку алгоритмы Прима и Обратного вызова создают идеальный лабиринт
     * необходимо создавать доп-пути через случайное удаление клеток
     */
    protected void createAdditionalPaths() {
        int numForAdditionPathsFormula = 4;
        int numAdditionalPaths = random.nextInt(maze.length * maze[0].length / numForAdditionPathsFormula) + 1;

        for (int i = 0; i < numAdditionalPaths; i++) {
            int row = random.nextInt(maze.length);
            int col = random.nextInt(maze[0].length);
            var cell = maze[row][col];
            var wallCells = walls.get(cell);

            if (!wallCells.isEmpty()) {
                var wallIndex = random.nextInt(wallCells.size());
                removeWall(cell, wallCells.get(wallIndex));
            }
        }
    }

    /**
     * Метод создает список несмежных вершин
     * При инициализации лабиринта в конструкторе для каждой клетки
     * появиться список несмежных клеток (между которым с ним стоит стенка)
     * после в процессе генерации в методе {@link #removeWall(Cell, Cell)}
     * будет удаляться стенка из списка
     */
    private void createWallsMap() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                var currentCell = maze[row][col];
                var wallCells = new ArrayList<>(getAllNeighbors(currentCell));
                walls.put(currentCell, wallCells);
            }
        }
    }

    public abstract Maze generate(GenerateParameters params);
}

package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import backend.academy.labirints.model.Maze;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressFBWarnings({"CLI_CONSTANT_LIST_INDEX", "PREDICTABLE_RANDOM"})
@SuppressWarnings({"MagicNumber"})
public abstract class Generator {
    protected final Random random = new Random();
    protected final Cell[][] maze;
    protected final Map<Cell, List<Cell>> adjacency = new HashMap<>();
    protected final Map<Cell, List<Cell>> walls = new HashMap<>();
    protected int width;
    protected int height;

    protected Generator(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.maze = new Cell[height][width];
        int id = 1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                var cell = Cell.builder()
                    .id(id++)
                    .coordinates(new Cell.Coordinates(j, i))
                    .type(Cell.CellType.NOTHING)
                    .isVisited(false)
                    .build();
                maze[i][j] = cell;
            }
        }
        createWallsMap();
    }

    protected List<Cell> getAllNeighbors(final Cell cell) {
        var neighbors = new ArrayList<Cell>();

        int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        for (var direction : directions) {
            var newRow = cell.y() + direction[1];
            var newCol = cell.x() + direction[0];

            if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length) {
                neighbors.add(maze[newRow][newCol]);
            }
        }

        return neighbors;
    }

    protected List<Cell> getUnvisitedNeighbors(final Cell cell) {
        return getAllNeighbors(cell).stream()
            .filter(c -> !c.isVisited())
            .toList();
    }

    public void generateRandomCells() {
        //TODO поменять генерацию: сделать больше поверхностей и разброс поменять
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            maze[y][x].type(random.nextInt(2) == 0 ? Cell.CellType.GOOD : Cell.CellType.BAD);
        }
    }

    /**
     * Метода добавляющий связь(проход) в adjacency и удаляет стенки в walls между клетками соседями cell, neighbour
     */
    protected void removeWall(final Cell cell, final Cell neighbour) {
        adjacency.computeIfAbsent(cell, _ -> new ArrayList<>()).add(neighbour);
        adjacency.computeIfAbsent(neighbour, _ -> new ArrayList<>()).add(cell);
        walls.getOrDefault(cell, new ArrayList<>()).remove(neighbour);
        walls.getOrDefault(neighbour, new ArrayList<>()).remove(cell);
    }

    //TODO поменять генерацию доп-путей: отделять стенки от граничных стенок
    protected void createAdditionalPaths() {
        int numAdditionalPaths = random.nextInt(maze.length * maze[0].length / 4) + 1;

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

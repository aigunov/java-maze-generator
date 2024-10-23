package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class Generator {
    protected int width, height;
    protected final Cell[][] maze;
    protected final Map<Cell, List<Cell>> adjacency = new HashMap<>();
    protected final Map<Cell, List<Cell>> walls = new HashMap<>();

    protected Generator(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.maze = new Cell[width][height];
        int id = 1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Cell(id++, i, j, false);
            }
        }
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
            var newRow = cell.x() + direction[0];
            var newCol = cell.y() + direction[1];

            if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length) {
                neighbors.add(maze[newRow][newCol]);
            }
        }

        return neighbors;
    }

    protected void removeWall(final Cell cell, final Cell neighbour) {
        adjacency.computeIfAbsent(cell, k -> new ArrayList<>()).add(neighbour);
        adjacency.computeIfAbsent(neighbour, k -> new ArrayList<>()).add(cell);
    }

    protected List<Cell> getUnvisitedNeighbors(final Cell cell) {
        return getAllNeighbors(cell).stream()
            .filter(c -> !c.isVisited())
            .toList();
    }

    protected void createAdditionalPaths(){
        createWallsMap();
        Random random = new Random();

        int numAdditionalPaths = random.nextInt(maze.length * maze[0].length / 4) + 1;

        for (int i = 0; i < numAdditionalPaths; i++) {
            int row = random.nextInt(maze.length);
            int col = random.nextInt(maze[0].length);
            Cell cell = maze[row][col];
            List<Cell> wallCells = walls.get(cell);

            if (!wallCells.isEmpty()) {
                int wallIndex = random.nextInt(wallCells.size());
                Cell wallCell = wallCells.get(wallIndex);
                adjacency.computeIfAbsent(cell, k -> new ArrayList<>()).add(wallCell);
                adjacency.computeIfAbsent(wallCell, k -> new ArrayList<>()).add(cell);
                walls.get(cell).remove(wallCell);
                walls.get(wallCell).remove(cell);
            }
        }
    }

    private void createWallsMap() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                Cell currentCell = maze[row][col];
                List<Cell> neighbors = adjacency.getOrDefault(currentCell, new ArrayList<>());
                List<Cell> wallCells = new ArrayList<>();


                // Проверяем все возможные смежные клетки
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        // Пропускаем текущую клетку
                        if (i == row && j == col) continue;

                        //Проверяем что это клетка угловая - не нужна
                        if(Math.abs(row - i) == Math.abs(col - j)) continue;

                        // Проверяем, находится ли клетка в пределах лабиринта
                        if (i >= 0 && i < maze.length && j >= 0 && j < maze[row].length) {
                            Cell neighborCell = maze[i][j];
                            if (!neighbors.contains(neighborCell)) {
                                wallCells.add(neighborCell);
                            }
                        }
                    }
                }
                walls.put(currentCell, wallCells);
            }
        }
    }
    public abstract Maze generate();
}

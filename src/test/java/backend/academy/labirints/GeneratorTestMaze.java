package backend.academy.labirints;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GeneratorTestMaze {
    private static Cell[][] maze = new Cell[3][3];
    private static Map<Cell, List<Cell>> adjacentCells = new HashMap<Cell, List<Cell>>();
    private static Map<Cell, List<Cell>> walls = new HashMap<Cell, List<Cell>>();
    private static Cell start, finish;
    private static List<Cell> path;

    public static Maze generateTestMaze(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                maze[i][j] = new Cell(new Cell.Coordinates(j, i), Cell.CellType.NOTHING, false);
                adjacentCells.put(maze[i][j], new ArrayList<Cell>());
                walls.put(maze[i][j], new LinkedList<Cell>());
            }
        }
        adjacentCells.get(maze[0][0]).add(maze[1][0]);
        walls.get(maze[0][0]).add(maze[0][1]);

        adjacentCells.get(maze[0][1]).add(maze[1][1]);
        walls.get(maze[0][1]).add(maze[0][0]);
        walls.get(maze[0][1]).add(maze[0][2]);

        adjacentCells.get(maze[0][2]).add(maze[1][2]);
        walls.get(maze[0][2]).add(maze[0][1]);

        adjacentCells.get(maze[1][0]).add(maze[1][1]);
        adjacentCells.get(maze[1][0]).add(maze[0][0]);
        adjacentCells.get(maze[1][1]).add(maze[1][2]);
        walls.get(maze[1][0]).add(maze[2][0]);

        adjacentCells.get(maze[1][1]).add(maze[1][0]);
        adjacentCells.get(maze[1][1]).add(maze[0][1]);
        adjacentCells.get(maze[1][1]).add(maze[1][2]);
        walls.get(maze[1][1]).add(maze[2][1]);

        adjacentCells.get(maze[1][2]).add(maze[0][2]);
        adjacentCells.get(maze[1][2]).add(maze[1][1]);
        adjacentCells.get(maze[1][2]).add(maze[2][2]);

        adjacentCells.get(maze[2][0]).add(maze[2][1]);
        walls.get(maze[2][0]).add(maze[1][0]);

        adjacentCells.get(maze[2][1]).add(maze[2][0]);
        adjacentCells.get(maze[2][1]).add(maze[2][2]);
        walls.get(maze[2][2]).add(maze[1][1]);

        adjacentCells.get(maze[2][2]).add(maze[1][2]);
        adjacentCells.get(maze[2][2]).add(maze[2][1]);


        start = maze[0][0];
        finish = maze[2][2];

        path = List.of(maze[0][0], maze[1][0], maze[1][1], maze[1][2], maze[2][2]);

        return Maze.builder()
            .maze(maze)
            .start(start)
            .finish(finish)
            .adjacentCells(adjacentCells)
            .walls(walls)
            .path(path)
            .build();
    }
}

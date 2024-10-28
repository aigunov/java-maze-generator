package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import backend.academy.labirints.model.Maze;
import java.util.ArrayDeque;
import java.util.Deque;

public class RecursiveBacktrackGenerator extends Generator {
    private final Deque<Cell> deque;

    public RecursiveBacktrackGenerator(final int width, final int height) {
        super(width, height);
        deque = new ArrayDeque<>();
    }

    @Override
    public Maze generate(final GenerateParameters params) {
        var start = maze[random.nextInt(height)][random.nextInt(width)];
        start.isVisited(true);
        deque.push(start);
        while (!deque.isEmpty()) {
            var cell = deque.peek();
            var neighbors = getUnvisitedNeighbors(cell);

            if (!neighbors.isEmpty()) {
                var neighbour = neighbors.get((int) (Math.random() * neighbors.size()));
                removeWall(cell, neighbour);
                neighbour.isVisited(true);
                deque.push(neighbour);
            } else {
                deque.pop();
            }
        }
        createAdditionalPaths();
        generateRandomCells();
        return Maze.builder()
            .maze(maze)
            .adjacentCells(adjacency)
            .walls(walls)
            .start(maze[params.start().y()][params.start().x()])
            .finish(maze[params.finish().y()][params.finish().x()])
            .build();
    }

}

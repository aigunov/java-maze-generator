package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import backend.academy.labirints.model.Maze;
import java.util.ArrayDeque;
import java.util.Deque;

public class RecursiveBacktrackGenerator extends Generator {
    private final Deque<Cell> tracker;

    public RecursiveBacktrackGenerator(final int width, final int height) {
        super(width, height);
        tracker = new ArrayDeque<>();
    }

    @Override
    public Maze generate(final GenerateParameters params) {
        var start = maze[random.nextInt(height)][random.nextInt(width)];
        start.isVisited(true);
        tracker.push(start);
        while (!tracker.isEmpty()) {
            var cell = tracker.peek();
            var neighbors = getUnvisitedNeighbors(cell);

            if (!neighbors.isEmpty()) {
                var neighbour = neighbors.get(random.nextInt(neighbors.size()));
                removeWall(cell, neighbour);
                neighbour.isVisited(true);
                tracker.push(neighbour);
            } else {
                tracker.pop();
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

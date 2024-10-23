package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.Comparator;
import java.util.Stack;

public class RecursiveBacktrackGenerator extends Generator {
    private final Stack<Cell> stack = new Stack<>();

    public RecursiveBacktrackGenerator(final int width, final int height) {
        super(width, height);
    }

    @Override
    public Maze generate() {
        var start = maze[random.nextInt(width)][random.nextInt(height)];
        start.isVisited(true);
        stack.push(start);
        while (!stack.isEmpty()) {
            var cell = stack.peek();
            var neighbors = getUnvisitedNeighbors(cell);

            if (!neighbors.isEmpty()) {
                var neighbour = neighbors.get((int) (Math.random() * neighbors.size()));
                removeWall(cell, neighbour);
                neighbour.isVisited(true);
                stack.push(neighbour);
            } else {
                stack.pop();
            }
        }
        createAdditionalPaths();
        generateRandomCells();
        return Maze.builder()
            .maze(maze)
            .adjacentCells(adjacency)
            .build();
    }

    public static void main(String[] args) {
        var generator = new RecursiveBacktrackGenerator(3, 3);
        generator.generate();

    }
}

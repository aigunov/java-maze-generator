package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.GenerateParameters;
import backend.academy.labirints.model.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimGenerator extends Generator {
    private final List<Cell> frontier = new ArrayList<>();

    public PrimGenerator(final int width, final int height) {
        super(width, height);
    }

    @Override
    public Maze generate(final GenerateParameters params) {
        var current = maze[random.nextInt(height)][random.nextInt(width)];
        current.isVisited(true);

        frontier.addAll(getUnvisitedNeighbors(current));
        Collections.shuffle(frontier);

        while (!frontier.isEmpty()) {
            current = frontier.getFirst();
            current.isVisited(true);

            var neighs = getAllNeighbors(current);
            neighs.removeIf(c -> !c.isVisited());

            if (!neighs.isEmpty()) {
                Collections.shuffle(neighs);
                removeWall(current, neighs.getFirst());
            }

            var neighsToFrontier = getUnvisitedNeighbors(current);
            frontier.addAll(neighsToFrontier);
            Collections.shuffle(frontier);

            frontier.removeIf(Cell::isVisited);
        }
        createAdditionalPaths();
        generateRandomCells();
        return Maze.builder()
            .adjacentCells(adjacency)
            .walls(walls)
            .maze(maze)
            .start(maze[params.start().y()][params.start().x()])
            .finish(maze[params.finish().y()][params.finish().x()])
            .build();
    }
}

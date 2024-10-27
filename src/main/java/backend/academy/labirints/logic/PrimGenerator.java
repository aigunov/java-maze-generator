package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PrimGenerator extends Generator {
    private final List<Cell> frontier = new ArrayList<>();

    public PrimGenerator(final int width, final int height) {
        super(width, height);
    }

    @Override
    public Maze generate() {
        var current = maze[random.nextInt(height)][random.nextInt(width)];
        current.isVisited(true);

        var neighsToFrontier = getUnvisitedNeighbors(current);
        frontier.addAll(neighsToFrontier);
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

            neighsToFrontier = getUnvisitedNeighbors(current);
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
            .build();
    }

    private void print() {
        var entrys = adjacency.entrySet().stream()
            .sorted(Comparator.comparing(k -> k.getKey().id())).toList();
        for(var entry: entrys){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        var generator = new PrimGenerator(4, 4);
        generator.generate();
        generator.print();
    }
}

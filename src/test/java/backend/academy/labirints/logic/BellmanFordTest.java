package backend.academy.labirints.logic;

import backend.academy.labirints.GeneratorTestMaze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BellmanFordTest {
    @Test
    void should_return_correct_path() {
        var solver = new BellmanFord();
        var maze = GeneratorTestMaze.generateTestMaze();
        var path = solver.findShortestPath(maze);
        System.out.println("Expected path: " + maze.path() + " Actual path: " + path);
        Assertions.assertEquals(maze.path(), path.reversed());
    }
}

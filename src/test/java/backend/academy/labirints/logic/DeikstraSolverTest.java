package backend.academy.labirints.logic;

import backend.academy.labirints.GeneratorTestMaze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeikstraSolverTest {

    @Test
    void should_return_correct_path() {
        var solver = new DeikstraSolver();
        var maze = GeneratorTestMaze.generateTestMaze();
        var path = solver.findShortestPath(maze);
        System.out.println("Expected path: " + maze.path() + " Actual path: " + path);
        Assertions.assertEquals(maze.path(), path);
    }

}

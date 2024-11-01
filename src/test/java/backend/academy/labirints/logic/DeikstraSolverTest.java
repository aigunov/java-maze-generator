package backend.academy.labirints.logic;

import backend.academy.labirints.GeneratorTestMaze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeikstraSolverTest {

    @Test
    void testFindShortestPathInLabyrinth() {
        var solver = new DeikstraSolver();
        var maze = GeneratorTestMaze.generateTestMaze();
        var path = solver.findShortestPath(maze);
        System.out.println("Expected path: " + maze.path() + " Actual path: " + path);
        Assertions.assertEquals(maze.path(), path);
    }

}

package backend.academy.labirints.logic;

import backend.academy.labirints.GeneratorTestMaze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BellmanFordTest {
    @Test
    void testFindShortestPathInLabyrinth() {
        var solver = new BellmanFord();
        var maze = GeneratorTestMaze.generateTestMaze();
        var path = solver.findShortestPath(maze);
        Assertions.assertEquals(maze.path().size(), path.reversed().size());
    }
}

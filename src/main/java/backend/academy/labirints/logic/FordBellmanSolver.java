package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import backend.academy.labirints.model.Point;
import java.util.List;
import java.util.Map;

public class FordBellmanSolver extends Solver {
    @Override
    public List<Cell> findShortestPath(Cell[][] cells, Map<Cell, List<Cell>> adjacency, Cell start, Cell finish) {
        return List.of();
    }
}

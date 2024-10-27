package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import java.util.List;
import java.util.Map;

public abstract class Solver {

    public abstract List<Cell> findShortestPath(Cell[][] cells, Map<Cell, List<Cell>> adjacency, Cell start, Cell finish);

}

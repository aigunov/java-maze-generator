package backend.academy.labirints.logic;

import backend.academy.labirints.model.Cell;
import backend.academy.labirints.model.Maze;
import java.util.List;
import java.util.Map;

public abstract class Solver {

    public abstract List<Cell> findShortestPath(final Maze maze);

}

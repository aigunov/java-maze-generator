package backend.academy.labirints.model;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class Maze {
    private final Cell start;
    private final Cell finish;
    private Cell[][] maze;
    private List<Cell> path;
    private Map<Cell, List<Cell>> adjacentCells;
    private Map<Cell, List<Cell>> walls;
}
